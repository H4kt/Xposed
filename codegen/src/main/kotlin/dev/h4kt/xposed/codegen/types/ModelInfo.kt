package dev.h4kt.xposed.codegen.types

import com.google.devtools.ksp.symbol.KSClassDeclaration
import com.google.devtools.ksp.symbol.KSType

internal data class ModelInfo(
    val modelClass: KSClassDeclaration,
    val idType: KSType,
    val columns: List<Column>
) {

    data class Column(
        val name: String,
        val type: KSType
    )

}
