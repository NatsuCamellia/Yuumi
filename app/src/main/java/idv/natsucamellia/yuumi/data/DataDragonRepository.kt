package idv.natsucamellia.yuumi.data

import idv.natsucamellia.yuumi.network.DataDragonApiService
import idv.natsucamellia.yuumi.network.SummonerSpell
import idv.natsucamellia.yuumi.network.getFullUrl
import kotlinx.coroutines.runBlocking

interface DataDragonRepository {
    fun getProfileIconUrl(id: Int): String
    fun getChampionIconUrl(id: Int): String
    fun getItemIconUrl(id: Int): String
    fun getSummonerSpellIconUrl(id: Int): String
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

    override fun getChampionName(id: Int): String {
        return championIdNameMap[id] ?: "Undefined"
    }
}