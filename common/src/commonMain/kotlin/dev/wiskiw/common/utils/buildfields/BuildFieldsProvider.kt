package dev.wiskiw.common.utils.buildfields

interface BuildFieldsProvider {

    // todo clean up
//    fun getBuildConfig(): BuildConfigFields
    fun getApiKeys(): ApiKeysFields
}