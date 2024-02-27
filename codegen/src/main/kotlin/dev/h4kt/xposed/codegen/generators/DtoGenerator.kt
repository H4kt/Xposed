package dev.h4kt.xposed.codegen.generators

import com.google.devtools.ksp.processing.CodeGenerator
import com.google.devtools.ksp.processing.Dependencies
import dev.h4kt.xposed.codegen.extensions.singular
import dev.h4kt.xposed.codegen.types.GeneratedClassReference
import dev.h4kt.xposed.codegen.types.ModelInfo

internal object DtoGenerator {

    private const val PACKAGE_NAME = "dev.h4kt.xposed.codegen.generated.dto"

    fun generate(
        codeGenerator: CodeGenerator,
        modelInfo: ModelInfo
    ): GeneratedClassReference {

        val (modelClass, idType, columns) = modelInfo

        val singularName = modelInfo.modelClass.simpleName.asString().singular()
        val dtoClassName = "${singularName}Dto"

        val imports = modelInfo
            .columns
            .map {
                it.type.declaration.qualifiedName!!.asString()
            }
            .toSet()

        val contents = """
            @file:UseSerializers(UUIDSerializer::class)
            
            package $PACKAGE_NAME
            
            import kotlinx.serialization.Serializable
            import kotlinx.serialization.UseSerializers
            
            import dev.h4kt.xposed.codegen.compat.UUIDSerializer

            import ${idType.declaration.qualifiedName!!.asString()}
            ${imports.joinToString("\n            ") { "import $it" }}
            
            @Serializable
            data class $dtoClassName(
                val id: ${idType.declaration.simpleName.asString()},
                ${columns.joinToString(",\n                ") { "val ${it.name}: ${it.type.declaration.simpleName.asString()}" }}
            )
            
        """.trimIndent()

        val dtoFileWriter = codeGenerator.createNewFile(
            dependencies = Dependencies(true, modelClass.containingFile!!),
            fileName = dtoClassName,
            packageName = PACKAGE_NAME
        )

        dtoFileWriter.use {
            it.write(contents.toByteArray())
        }

        return GeneratedClassReference(
            simpleName = dtoClassName,
            qualifiedName = "$PACKAGE_NAME.$dtoClassName"
        )
    }

}
