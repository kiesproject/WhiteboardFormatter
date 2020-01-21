package com.example.whiteboardformatter.save_page

import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.whiteboardformatter.data.model.TextForPreview
import com.example.whiteboardformatter.data.repository.Repository

class SaveViewModel(private val repository: Repository):ViewModel(){

    private var _previewText = MutableLiveData<String>("")
    val previewText : LiveData<String> = _previewText

    private var _textVisibility = MutableLiveData<Int>(View.VISIBLE)
    var textVisibility:LiveData<Int> = _textVisibility

    private var _mdVisibility = MutableLiveData<Int>(View.INVISIBLE)
    var mdVisibility :LiveData<Int> = _mdVisibility

    fun start(textArray:Array<TextForPreview>){

        //デフォルトをテキストプレビューに設定
        _textVisibility.value = View.VISIBLE
        _mdVisibility.value = View.INVISIBLE


        //テキストプレビューの作成
        var textResult :String = ""     //textの結果格納用
        var beforeText  : TextForPreview = TextForPreview("",0,0,0,0,1f,1f)
        textArray.sortBy { it.x }       //配列をy,xの優先度でソート
        textArray.sortBy { it.y }

        textArray.forEach {
            if(beforeText.y+beforeText.height*0.5f < it.y){     //高さを判断して、改行をresultに加える
                val spaceHeight = it.y -beforeText.y + beforeText.height    //前のテキストとの縦方向の距離を求める
                val numberOfIndention =spaceHeight/it.height                //改行数
                for(i in 0 until numberOfIndention){
                    textResult+="\n"
                }
                beforeText = beforeText.copy(x=0,y = beforeText.y+beforeText.height,width = 0)  //改行したら、x,y,widthを修正
            }

            //Tabの計算        空白の数 = 左にあるテキストとの距離 / (テキストの幅/テキストの文字数)
            //Tab = 空白の数 / 4
            val numberOfTabs =
                if(beforeText.x+beforeText.width < it.x){
                    val spaceWidth = it.x -beforeText.x + beforeText.width
                    spaceWidth/(it.width/it.text.length)/4
                }else   0
            //Tabの追加
            for(i in 0 until numberOfTabs){
                textResult+="\t"
            }
            textResult+=it.text

            beforeText = it
        }
        //結果を格納
        _previewText.value = textResult



        //mdプレビューの作成





    }
}