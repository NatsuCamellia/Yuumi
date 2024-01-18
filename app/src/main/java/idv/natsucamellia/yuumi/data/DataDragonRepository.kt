package idv.natsucamellia.yuumi.data

import idv.natsucamellia.yuumi.network.ddragon.DataDragonApiService
import idv.natsucamellia.yuumi.network.ddragon.Rune
import idv.natsucamellia.yuumi.network.ddragon.RuneStyle
import idv.natsucamellia.yuumi.network.ddragon.SummonerSpell
import idv.natsucamellia.yuumi.network.ddragon.getFullUrl
import kotlinx.coroutines.runBlocking

interface DataDragonRepository {
    fun getProfileIconUrl(id: Int): String
    fun getChampionIconUrl(id: Int): String
    fun getItemIconUrl(id: Int): String
    fun getSummonerSpellIconUrl(id: Int): String
    fun getRuneStyleIconUrl(id: Int): String
    fun getRuneIconUrl(id: Int): String
    fun getChampionName(id: Int): String

}

class NetworkDataDragonRepository(
    private val dataDragonApiService: DataDragonApiService
): DataDragonRepository {
    private val versions by lazy {
        runBlocking {
            dataDragonApiService.getVersions()
        }
    }
    private val version = versions[0]
    private val championIdNameMap: Map<Int, String> by lazy {
        runBlocking {
            dataDragonApiService.getChampions(version = version)
                .data
                .map{
                    it.value.key.toInt() to it.key
                }.toMap()
        }
    }
    private val summonerSpellMap: Map<Int, SummonerSpell> by lazy {
        runBlocking {
            dataDragonApiService.getSummonerSpells(version = version)
                .data
                .mapKeys {
                    it.value.key.toInt()
                }
        }
    }
    private val runeStyles: List<RuneStyle> by lazy {
        runBlocking {
            dataDragonApiService.getRuneStyles(version)
        }
    }
    private val idRuneStyleMap: Map<Int, RuneStyle> by lazy {
        runeStyles.associateBy {
            it.id
        }
    }
    private val idRuneMap: Map<Int, Rune> by lazy {
        val map = mutableMapOf<Int, Rune>()
        runeStyles.forEach {
            it.slots.forEach { x ->
                x.runes.forEach { r ->
                    map[r.id] = r
                }
            }
        }
        map
    }

    override fun getProfileIconUrl(id: Int): String {
        return "https://ddragon.leagueoflegends.com/cdn/${version}/img/profileicon/${id}.png"
    }

    override fun getChampionIconUrl(id: Int): String {
        val name = championIdNameMap[id]
        return "https://ddragon.leagueoflegends.com/cdn/${version}/img/champion/${name}.png"
    }

    override fun getItemIconUrl(id: Int): String {
        return "https://ddragon.leagueoflegends.com/cdn/${version}/img/item/${id}.png"
    }

    override fun getSummonerSpellIconUrl(id: Int): String {
        val summonerSpell = summonerSpellMap[id]!!
        return summonerSpell.image.getFullUrl(version)
    }

    override fun getRuneStyleIconUrl(id: Int): String {
        val runeStyle = idRuneStyleMap[id]
        return "https://ddragon.leagueoflegends.com/cdn/img/${runeStyle?.icon}"
    }

    override fun getRuneIconUrl(id: Int): String {
        val rune = idRuneMap[id]
        return "https://ddragon.leagueoflegends.com/cdn/img/${rune?.icon}"
    }

    override fun getChampionName(id: Int): String {
        return championIdNameMap[id] ?: "Undefined"
    }
}