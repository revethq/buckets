package com.revethq.buckets.service

interface EncryptionService {
    fun encrypt(plaintext: String): String

    fun decrypt(ciphertext: String): String
}
