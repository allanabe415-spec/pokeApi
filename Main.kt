import com.google.gson.Gson
import java.net.URI
import java.net.http.HttpClient
import java.net.http.HttpRequest
import java.net.http.HttpResponse
import java.util.Scanner

fun main() {
    val leitor = Scanner(System.`in`)
    print("Digite um ID: ")
    val buscador = leitor.nextInt()

    val client: HttpClient  = HttpClient.newHttpClient()
    val request = HttpRequest.newBuilder()
        .uri(URI.create("https://pokeapi.co/api/v2/pokemon/$buscador"))
        .build()
    val response = client.send(request, HttpResponse.BodyHandlers.ofString())

    val dados = response.body()

    val gson = Gson()
    val resultado = runCatching {
        val buscaApi = gson.fromJson(dados,  Informacoes::class.java)

        val meuPokemon = Pokemon( buscaApi.name, buscaApi .abilities)

        println(meuPokemon.nome.replaceFirstChar { it.uppercase() })
        println("Habilidades: ")
        for (i in meuPokemon.Habilidades){
            println(i.ability.name.replaceFirstChar { it.uppercase() })
        }
    }
    resultado.onFailure{
        print("Pokemon não encontrado!!")}
}
