package dev.wiskiw.shared.utils.buildfields

interface BuildFieldsProvider {

    fun getBuildConfig(): BuildConfigFields
    fun getApiKeys(): ApiKeysFields
}