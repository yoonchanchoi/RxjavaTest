package com.example.view.rxjavatest
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import io.reactivex.Observable.fromArray
import io.reactivex.Observable.just
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

    val arrayTest = arrayListOf<String>("str1","str2","str3","str4")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        init()
    }

    private fun init(){
        test1()
        test2()
    }

    private fun test1(){
        io.reactivex.Observable.fromIterable(urls)
            .concatMap{ result ->
                request(result).toObservable()
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

    private fun test2(){
        val source = fromArray(arrayTest)
        source.concatMap {
            Log.e("cyc","it-->$it")
            it.forEach { str ->
                str+"추가"
            }
            Log.e("cyc","concatmap 결과 -->$it")
            return@concatMap just(it)
        }
//            .concatMap {
//                Log.e("cyc","it-->$it")
//
//            }
        //        val source2 = Observable.fromArray<String>(arrayTest)
    }



}