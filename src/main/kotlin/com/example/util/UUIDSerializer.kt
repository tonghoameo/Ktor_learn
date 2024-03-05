package com.example.util


import kotlinx.serialization.*
import kotlinx.serialization.json.*
import kotlinx.serialization.encoding.*
import kotlinx.serialization.descriptors.*

import java.util.UUID

class UUIDSerializer: KSerializer<UUID> {
    override val descriptor: SerialDescriptor 
        get() = PrimitiveSerialDescriptor("UUID", PrimitiveKind.STRING)

    override fun serialize(encoder: Encoder, value: UUID) {
        encoder.encodeString(value.toString())
    }

    override fun deserialize(decoder: Decoder): UUID = UUID.fromString(decoder.decodeString())
}
