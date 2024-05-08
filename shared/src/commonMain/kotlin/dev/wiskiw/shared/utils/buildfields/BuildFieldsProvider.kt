package dev.wiskiw.shared.utils.buildfields

interface BuildFieldsProvider {

    // todo clean up
//    fun getBuildConfig(): BuildConfigFields
    fun getApiKeys(): ApiKeysFields
}