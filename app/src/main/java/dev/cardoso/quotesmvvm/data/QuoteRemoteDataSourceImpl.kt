package dev.cardoso.quotesmvvm.data

import dev.cardoso.quotesmvvm.data.model.QuoteModel
import dev.cardoso.quotesmvvm.data.model.QuoteResponse
import dev.cardoso.quotesmvvm.data.remote.QuoteApi
import dev.cardoso.quotesmvvm.data.remote.QuoteApiImpl
import dev.cardoso.quotesmvvm.data.remote.QuoteRemoteDataSource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import org.json.JSONObject
import org.json.JSONTokener
import javax.inject.Inject


class QuoteRemoteDataSourceImpl @Inject constructor(private var quoteApi:QuoteApi): QuoteRemoteDataSource  {
    override suspend fun getQuotes(): Flow<List<QuoteModel>?> {
        val response =  quoteApi.getQuotes()
        return (response.body().let {
            flow { emit(it) }
        })
    }

    override suspend fun editQuote(quoteModel: QuoteModel, token: String): Flow<QuoteResponse?> {
        val response = quoteApi.editQuote(token, quoteModel.id, quoteModel)

        return (when(response.isSuccessful){
            true -> {
                response.body().let {
                    flow {
                        if (it != null){
                            emit(it)
                        }
                    }
                }
            }
            else -> {
                val jsonObject = JSONTokener(response.errorBody()?.string()).nextValue() as JSONObject
                val quoteResponse = QuoteResponse(
                    success = false,
                    message = jsonObject.getString("message"), data = listOf()
                )
                flow { emit(quoteResponse) }
            }
        })
    }
}