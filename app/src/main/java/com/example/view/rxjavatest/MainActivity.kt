package com.example.view.rxjavatest

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import io.reactivex.Observable.*
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.functions.BiFunction
import io.reactivex.rxkotlin.addTo
import io.reactivex.schedulers.Schedulers
import java.time.temporal.Temporal
import java.util.*
import java.util.concurrent.TimeUnit
import kotlin.collections.forEach
import kotlin.random.Random


class MainActivity : AppCompatActivity() {

    private val disposables by lazy { CompositeDisposable() }

    val startTime = Date().time
    val urls = listOf(
        "https://naver-api-1.com",
        "https://google-api-2.com",
        "https://samsung-api-3.com",
        "https://kakao-api-4.com",
        "https://line-api-5.com"
    )

    val arrayTest = arrayListOf<String>("str1", "str2", "str3", "str4")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        init()
    }

    private fun init() {
//        test1()
//        test2()
        test4()
    }

    private fun test1() {
        io.reactivex.Observable.fromIterable(urls)
            .concatMap { result ->
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

//    private fun test2() {
//        Log.e("cyc", "test2")
//        val source = fromIterable(arrayTest)
////        val source = fromArray(arrayTest)
//        Log.e("cyc", "test2--source--->")
//        source.subscribeOn(Schedulers.io())
//            .observeOn(AndroidSchedulers.mainThread())
//            .concatMap {
//
//                Log.e("cyc", "it-->$it")
//                val tempStr = StringBuilder()
//                it.forEach { str ->
//                    Log.e("cyc","추가")
//                    str + "추가"
//                    tempStr.append(str)
//                    tempStr.append("추가")
//                    Log.e("cyc","str-->${str}")
//                    Log.e("cyc","tempStr-->${tempStr}")
//
//                }
//                io.reactivex.Observable.just(tempStr)
//
//            }
//            .subscribe({
//                Log.e("cyc", "it--subscribe-->$it")
//            },{
//                Log.e("cyc", "error")
//            }).addToDisposables()
////            .concatMap {
////                Log.e("cyc","it-->$it")
////
////            }
//        //        val source2 = Observable.fromArray<String>(arrayTest)
//    }


//    fun testMoth(str: String): String{
//        str+"추가"
//    }

    private fun test2() {
        Log.e("cyc", "test2")
        val source = fromArray(arrayTest)
//        val source = fromIterable(arrayTest)
        source.subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .concatMap { strArray->
//                strArray + "추가"
//                Log.e("cyc","strArray-->$strArray")
                strArray.map {
                    it+"추가"
                }
                Log.e("cyc","concatmapResult-->$strArray")
                return@concatMap strArray
            }
            .subscribe({
                Log.e("cyc", "it--subscribe--test2--->>$it")
            },{
                Log.e("cyc", "error")
            }).addToDisposables()
//            .concatMap {
//                Log.e("cyc","it-->$it")
//
//            }
        //        val source2 = Observable.fromArray<String>(arrayTest)
    }


    private fun test3() {
        Log.e("cyc", "test2")
        val source = fromIterable(arrayTest)
        Log.e("cyc", "test2--source--->")
        source.subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .flatMap {
                return@flatMap just(it+"추가")
//                return just(it+"추가")
            }
            .subscribe({
                Log.e("cyc", "it--subscribe-->$it")
            },{
                Log.e("cyc", "error")
            }).addToDisposables()
//            .concatMap {
//                Log.e("cyc","it-->$it")
//            }
        //        val source2 = Observable.fromArray<String>(arrayTest)
    }
    private fun test4(){
        val source=fromArray(arrayOf("s1","s2","s3","s4"))
        source.subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .concatMap {
                it.map {
                    it+"a"
                }.run {
                    Log.e("cyc","삐질-run->$this")
                    just(this)
                }
            }
            .subscribe {
                Log.e("cyc","삐질-subscribe->$it")

                it
            }
    }

    private fun Disposable.addToDisposables(): Disposable = addTo(disposables)
}