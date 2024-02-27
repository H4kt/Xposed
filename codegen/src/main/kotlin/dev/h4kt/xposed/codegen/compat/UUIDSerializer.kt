package dev.h4kt.xposed.codegen.compat

import kotlinx.serialization.KSerializer
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import java.util.*

object UUIDSerializer : KSerializer<UUID> {

    override val descriptor = PrimitiveSerialDescriptor(
        serialName = "UUID",
        kind = PrimitiveKind.STRING
    )

    override fun serialize(
        encoder: Encoder,
        value: UUID
    ) {
        encoder.encodeString(value.toString())
    }

    override fun deserialize(decoder: Decoder): UUID {
        return decoder.decodeString().run(UUID::fromString)
    }

}
