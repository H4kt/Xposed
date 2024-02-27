package dev.h4kt.xposed.codegen.ksp

import com.google.devtools.ksp.getClassDeclarationByName
import com.google.devtools.ksp.getDeclaredProperties
import com.google.devtools.ksp.processing.CodeGenerator
import com.google.devtools.ksp.processing.KSPLogger
import com.google.devtools.ksp.processing.Resolver
import com.google.devtools.ksp.processing.SymbolProcessor
import com.google.devtools.ksp.symbol.KSAnnotated
import com.google.devtools.ksp.symbol.KSClassDeclaration
import com.google.devtools.ksp.validate
import dev.h4kt.xposed.annotations.ExperimentalXposedApi
import dev.h4kt.xposed.codegen.annotations.Model
import dev.h4kt.xposed.codegen.generators.DtoGenerator
import dev.h4kt.xposed.codegen.generators.RepositoryGenerator
import dev.h4kt.xposed.codegen.types.ModelInfo
import org.jetbrains.exposed.dao.id.IdTable
import org.jetbrains.exposed.sql.Column

@OptIn(ExperimentalXposedApi::class)
internal class ModelAnnotationProcessor(
    private val logger: KSPLogger,
    private val codeGenerator: CodeGenerator
) : SymbolProcessor {

    override fun process(resolver: Resolver): List<KSAnnotated> {

        val ksIdTableType = resolver.getClassDeclarationByName<IdTable<*>>()!!
        val ksColumnType = resolver.getClassDeclarationByName<Column<*>>()!!

        val annotatedSymbols = resolver.getSymbolsWithAnnotation(Model::class.qualifiedName!!)

        val annotatedModelClasses = annotatedSymbols
            .filter { it.validate() }
            .filterIsInstance<KSClassDeclaration>()
            .filter {
                ksIdTableType.asStarProjectedType().isAssignableFrom(it.asStarProjectedType())
            }
            .toSet()

        annotatedModelClasses.forEach { modelClass ->

            val declaredColumnProperties = modelClass.getDeclaredProperties()
                .filter { it.type.resolve().declaration == ksColumnType }

            val idType = modelClass
                .getAllProperties()
                .first { it.simpleName.asString() == "id" }
                .type
                .resolve()
                .arguments
                .first()
                .type!!
                .resolve()
                .arguments
                .first()
                .type!!
                .resolve()

            val columns = declaredColumnProperties
                .map { property ->

                    val valueType = property
                        .type
                        .resolve()
                        .arguments
                        .first()
                        .type!!
                        .resolve()

                    return@map ModelInfo.Column(
                        name = property.simpleName.asString(),
                        type = valueType
                    )
                }
                .toList()

            val modelInfo = ModelInfo(
                modelClass = modelClass,
                idType = idType,
                columns = columns
            )

            val dtoClass = DtoGenerator.generate(codeGenerator, modelInfo)
            val (repositoryInterface, repositoryImplementationClass) = RepositoryGenerator.generate(codeGenerator, modelInfo, dtoClass)

        }

        return annotatedSymbols.toList() - annotatedModelClasses
    }

}
