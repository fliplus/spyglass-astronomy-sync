plugins {
    alias(libs.plugins.stonecutter)
}

stonecutter parameters {
    swaps["mod_id"] = "\"${property("mod.id")}\";"
}

stonecutter active "26.3"
