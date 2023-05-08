package com.example.view.rxjavatest
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import io.reactivex.Flowable
import io.reactivex.Single
import io.reactivex.functions.BiFunction
import java.util.*
import java.util.concurrent.TimeUnit
import kotlin.random.Random


class MainActivity : AppCompatActivity() {



    val startTime = Date().time
    val urls = listOf(
        "https://naver-api-1.com",
        "https://google-api-2.com",
        "https://samsung-api-3.com",
        "https://kakao-api-4.com",
        "https://line-api-5.com"
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        init()
    }

    private fun init(){
        Flowable.fromIterable(urls)
            .concatMapEager { result ->
                request(result).toFlowable()
            }.subscribe({
                println("${Date().time - startTime} $it")
            }, { error ->
                error.printStackTrace()
            }, {
                println("${Date().time - startTime} complete")
            })

        Thread.sleep(3000L)
        println("Process finished")
    }



    fun request(url: String): Single<String> {
        return Single.zip(
            Single.timer(Random.nextLong(2000), TimeUnit.MILLISECONDS),
            Single.just(url),
            BiFunction<Long, String, String> { _, url ->
                "$url response"
            })
    }




}