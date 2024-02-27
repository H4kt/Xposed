package dev.h4kt.xposed.codegen.generators

import com.google.devtools.ksp.processing.CodeGenerator
import com.google.devtools.ksp.processing.Dependencies
import dev.h4kt.xposed.codegen.extensions.singular
import dev.h4kt.xposed.codegen.types.GeneratedClassReference
import dev.h4kt.xposed.codegen.types.ModelInfo

internal object RepositoryGenerator {

    private const val PACKAGE_NAME = "dev.h4kt.xposed.codegen.generated.repositories"

    fun generate(
        codeGenerator: CodeGenerator,
        modelInfo: ModelInfo,
        dtoClass: GeneratedClassReference
    ): Pair<GeneratedClassReference, GeneratedClassReference> {

        val (modelClass) = modelInfo

        val singularName = modelClass.simpleName.asString().singular()

        val packageName = "$PACKAGE_NAME.${singularName.lowercase()}"
        val interfaceName = "${singularName}Repository"
        val implementationClassName = "Xposed$interfaceName"

        generateInterface(
            codeGenerator = codeGenerator,
            modelInfo = modelInfo,
            dtoClass = dtoClass,
            packageName = packageName,
            interfaceName = interfaceName
        )

        generateImplementation(
            codeGenerator = codeGenerator,
            modelInfo = modelInfo,
            dtoClass = dtoClass,
            interfaceName = interfaceName,
            packageName = packageName,
            implementationClassName = implementationClassName
        )

        val interfaceReference = GeneratedClassReference(
            simpleName = interfaceName,
            qualifiedName = "$packageName.$interfaceName"
        )

        val implementationClassReference = GeneratedClassReference(
            simpleName = implementationClassName,
            qualifiedName = "$packageName.$implementationClassName"
        )

        return interfaceReference to implementationClassReference
    }

    private fun generateInterface(
        codeGenerator: CodeGenerator,
        modelInfo: ModelInfo,
        dtoClass: GeneratedClassReference,
        packageName: String,
        interfaceName: String
    ) {

        val (modelClass, idType) = modelInfo
        val dtoClassName = dtoClass.simpleName
        val idClassName = idType.declaration.simpleName.asString()

        val contents = """
            package $packageName
            
            import dev.h4kt.xposed.codegen.types.PageResult
            
            import ${idType.declaration.qualifiedName!!.asString()}
            import ${dtoClass.qualifiedName}
            
            interface $interfaceName {
            
                fun create(
                    dto: $dtoClassName
                ): $dtoClassName
                
                fun findById(
                    id: $idClassName
                ): $dtoClassName?
                
                fun findAll(
                    offset: Long = 0,
                    limit: Int = 100
                ): PageResult<$dtoClassName>
                
                fun update(
                    id: $idClassName,
                    dto: $dtoClassName
                )
                
                fun delete(id: UUID)
            
            }
            
        """.trimIndent()

        val fileWriter = codeGenerator.createNewFile(
            dependencies = Dependencies(true, modelClass.containingFile!!),
            fileName = interfaceName,
            packageName = packageName
        )

        fileWriter.use {
            it.write(contents.toByteArray())
        }

    }

    private fun generateImplementation(
        codeGenerator: CodeGenerator,
        modelInfo: ModelInfo,
        dtoClass: GeneratedClassReference,
        interfaceName: String,
        packageName: String,
        implementationClassName: String
    ) {

        val (modelClass, idType, columns) = modelInfo

        val idClassName = idType.declaration.simpleName.asString()
        val dtoClassName = dtoClass.simpleName
        val modelClassName = modelClass.simpleName.asString()

        val contents = """
            package $packageName
            
            import dev.h4kt.xposed.extensions.findById
            import dev.h4kt.xposed.extensions.updateById
            import dev.h4kt.xposed.extensions.deleteById
            
            import dev.h4kt.xposed.codegen.types.PageResult
            
            import org.jetbrains.exposed.sql.insert
            import org.jetbrains.exposed.sql.selectAll
            import org.jetbrains.exposed.sql.ResultRow
            
            import ${idType.declaration.qualifiedName!!.asString()}
            import ${modelClass.qualifiedName!!.asString()}
            import ${dtoClass.qualifiedName}
            
            open class $implementationClassName : $interfaceName {
                
                override fun create(
                    dto: $dtoClassName
                ): $dtoClassName {
                    
                    val insertedRow = $modelClassName.insert {
                        ${columns.joinToString("\n                        ") { column ->
                            "it[this.${column.name}] = dto.${column.name}"
                        }}
                    }
                    
                    return dto.copy(
                        id = insertedRow[$modelClassName.id].value
                    )
                }
                
                override fun findById(
                    id: $idClassName
                ): $dtoClassName? {
                    return $modelClassName.findById(id)?.to$dtoClassName()
                }
                
                override fun findAll(
                    offset: Long,
                    limit: Int
                ): PageResult<$dtoClassName> {
                    
                    val total = $modelClassName
                        .selectAll()
                        .count()
                    
                    val items = $modelClassName
                        .selectAll()
                        .limit(n = limit, offset = offset)
                        .map {
                            it.to$dtoClassName()
                        }
                    
                    return PageResult(
                        offset = offset,
                        limit = limit,
                        total = total,
                        items = items
                    )
                }
                
                override fun update(
                    id: $idClassName,
                    dto: $dtoClassName
                ) {
                    $modelClassName.updateById(id) {
                        ${columns.joinToString("\n                        ") { column ->
                            "it[this.${column.name}] = dto.${column.name}"
                        }}
                    } 
                }
                
                override fun delete(
                    id: $idClassName
                ) {
                    $modelClassName.deleteById(id)
                }
                
                protected fun ResultRow.to$dtoClassName(): $dtoClassName {
                    return $dtoClassName(
                        id = this[$modelClassName.id].value,
                        ${columns.joinToString(",\n                        ") { column ->
                            "${column.name} = this[$modelClassName.${column.name}]"
                        }}
                    )
                }
                
            }
            
        """.trimIndent()

        val fileWriter = codeGenerator.createNewFile(
            dependencies = Dependencies(true, modelClass.containingFile!!),
            fileName = implementationClassName,
            packageName = packageName
        )

        fileWriter.use {
            it.write(contents.toByteArray())
        }

    }

}
