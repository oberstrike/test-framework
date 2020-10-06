package com.maju.util


fun <K, V> MutableMap<K, V>.put(pair: Pair<K, V>) {
    put(pair.first, pair.second)
}

fun <K, V> MutableMap<K, V>.putAll(pairs: List<Pair<K, V>>) {
    pairs.forEach { put(it) }
}

