package dev.cardoso.quotesmvvm.data


import dev.cardoso.quotesmvvm.core.toEntity
import dev.cardoso.quotesmvvm.core.toListQuoteModel
import dev.cardoso.quotesmvvm.core.toQuoteModel
import dev.cardoso.quotesmvvm.data.local.QuoteLocalDataSource
import dev.cardoso.quotesmvvm.data.local.daos.QuoteDAO
import dev.cardoso.quotesmvvm.data.model.QuoteModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
/*
class QuoteLocalDataSourceImpl @Inject constructor(var quoteDAO: QuoteDAO):QuoteLocalDataSource {
    override fun getQuotes(): Flow<List<QuoteModel>> {
        val quotes = quoteDAO.getQuotes()
        val quotesMapped= quotes.map {
            it.map { quoteEntity ->
                quoteEntity.toQuoteModel()
            }
        }
        return  quotesMapped
    }

    override  fun getQuote(quoteId: Int): Flow<QuoteModel> {
        return   quoteDAO.getQuote(quoteId).map {
            it.toQuoteModel()
        }

    }

    override fun getQuoteRandom(): Flow<QuoteModel> {
        return  quoteDAO.getQuoteRandom().map{
            it.toQuoteModel()
        }
    }

    override suspend fun insertAll(quotes: List<QuoteModel>) {
        quoteDAO.insertAll(quotes!!.map {
            it.toQuoteEntity()
        })
    }


    override suspend fun insert(quoteModel: QuoteModel) {
        quoteDAO.insert(
                quoteModel.toQuoteEntity()
             )
    }

}
*/

class QuoteLocalDataSourceImpl  @Inject constructor(private val quoteDao: QuoteDAO): QuoteLocalDataSource {
    override fun getQuotes(): Flow<List<QuoteModel>> {
        val quotes = quoteDao.getQuotes()
        return quotes.map { it.toListQuoteModel() }
    }

    override   fun getQuote(quoteId: Int): Flow<QuoteModel> {
        return  quoteDao.getQuote(quoteId).map { it.toQuoteModel()}
    }

    override fun getQuoteRandom(): Flow<QuoteModel> {
        return  quoteDao.getQuoteRandom().map { it.toQuoteModel() }
    }

    override suspend fun insertAll(quotes: List<QuoteModel>) {
        quoteDao.insertAll(quotes!!.map { it.toEntity()})
    }


    override suspend fun insert(quoteModel: QuoteModel) {
        quoteDao.insert(quoteModel.toEntity())
    }

}




