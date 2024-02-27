package dev.h4kt.xposed.codegen.ksp

import com.google.devtools.ksp.processing.SymbolProcessor
import com.google.devtools.ksp.processing.SymbolProcessorEnvironment
import com.google.devtools.ksp.processing.SymbolProcessorProvider

internal class ModelAnnotationProcessorProvider : SymbolProcessorProvider {

    override fun create(
        environment: SymbolProcessorEnvironment
    ): SymbolProcessor {
        return ModelAnnotationProcessor(
            logger = environment.logger,
            codeGenerator = environment.codeGenerator
        )
    }

}
