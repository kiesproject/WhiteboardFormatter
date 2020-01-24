package com.example.whiteboardformatter.save_page

import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.whiteboardformatter.data.model.TextForPreview
import com.example.whiteboardformatter.data.repository.Repository
import com.example.whiteboardformatter.util.until

class SaveViewModel(private val repository: Repository) : ViewModel() {

    var switchFlg = MutableLiveData<Boolean>(false)

    private var _previewText = MutableLiveData<String>("")
    val previewText : LiveData<String> = _previewText

    private var _textVisibility = MutableLiveData<Int>(View.VISIBLE)
    var textVisibility:LiveData<Int> = _textVisibility

    private var _mdVisibility = MutableLiveData<Int>(View.INVISIBLE)
    var mdVisibility :LiveData<Int> = _mdVisibility
  
    companion object {
        const val SHAPE = "#"
        const val SPACE = " "

        val H1_RANGE = 1.75f.until(Float.MAX_VALUE)
        val H2_RANGE = 1.3125f.until(1.75f)
        val H3_RANGE = 1.0625f.until(1.13125f)
        val H4_RANGE = 0.875f.until(1.0625f)
        val H5_RANGE = 0.6875f.until(0.875f)
        val H6_RANGE = (-Float.MIN_VALUE).until(0.6875f)
        val FONT_SIZE_RANGE = arrayOf(H1_RANGE, H2_RANGE, H3_RANGE, H4_RANGE, H5_RANGE, H6_RANGE)
    }

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
        var mdResult: String = ""
        var beforeMdText: TextForPreview = TextForPreview("", 0, 0, 0, 0, 1f, 1f)

        textArray.forEach {
            if (beforeMdText.y + beforeMdText.height * 0.5f < it.y) {     //高さを判断して、改行をresultに加える
                val spaceHeight =
                    it.y - beforeMdText.y + beforeMdText.height    //前のテキストとの縦方向の距離を求める
                val numberOfIndention = spaceHeight / it.height                //改行数
                for (i in 0 until numberOfIndention) {
                    mdResult += "\n"
                }
                beforeMdText = beforeMdText.copy(
                    x = 0,
                    y = beforeMdText.y + beforeMdText.height,
                    width = 0
                )  //改行したら、x,y,widthを修正
            }

            //フォントサイズの計算
            if (beforeMdText.x == 0) {
                for (range in FONT_SIZE_RANGE) {
                    if (it.scaleX in range) {
                        val index = FONT_SIZE_RANGE.indexOf(range)
                        if (index != 3) {
                            for (i in 0..index) {
                                mdResult += (SHAPE + SPACE)
                            }
                        }
                        break
                    }
                }
            } else {
                //空白の計算        空白の数 = 左にあるテキストとの距離 / (テキストの幅/テキストの文字数)
                val numberOfSpaces =
                    if (beforeMdText.x + beforeMdText.width < it.x) {
                        val spaceWidth = it.x - beforeMdText.x + beforeMdText.width
                        spaceWidth / (it.width / it.text.length)
                    } else 0
                //SPACEの追加
                for (i in 0 until numberOfSpaces) {
                    mdResult += SPACE
                }
            }

            mdResult += it.text
            beforeMdText = it

        }
        _previewMdText.value = mdResult
    }
}