package com.example.xml_prac

import android.graphics.Color
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.xml_prac.databinding.ActivityPieChartBinding
import com.github.mikephil.charting.animation.Easing
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import com.google.firebase.crashlytics.buildtools.reloc.com.google.common.reflect.TypeToken
import com.google.gson.Gson

class PieChartActivity : AppCompatActivity() {
    private val binding: ActivityPieChartBinding by lazy {
        ActivityPieChartBinding.inflate(layoutInflater)
    }

    val dataList: List<PieEntry> = listOf(
        PieEntry(10F, "감자"),
        PieEntry(30F, "고구마"),
        PieEntry(25F, "배추"),
        PieEntry(35F, "양파"),
        PieEntry(20F, "고추"),
        PieEntry(50F, "삼겹살"),
        PieEntry(20F, "콩나물")
    )



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        initView()
    }

    fun initView() {
        val dataSet = PieDataSet(dataList, "")

        dataSet.colors = listOf(
            Color.RED,
            Color.BLUE,
            Color.CYAN,
            Color.GRAY,
            Color.DKGRAY,
            Color.BLACK,
            Color.GREEN,
            )

        dataSet.valueTextSize = 16F
        dataSet.setDrawValues(false) // value 비활성화

        val pieData = PieData(dataSet)

        binding.run {
            pieChart.apply {
                data = pieData
                description.isEnabled = false // 차트 설명 비활성화
                legend.isEnabled = false // 하단 설명 비활성화
                isRotationEnabled = true // 차트 회전 활성화
                setDrawEntryLabels(false) // 엔트리 라벨 비활성화
                setEntryLabelColor(Color.BLACK) // label 색상
                animateY(1400, Easing.EaseInOutQuad) // 1.4초 동안 애니메이션 설정
                animate()
            }

            // Gson 객체 생성
            val gson = Gson()

            // 원본 데이터를 JSON 형식으로 직렬화
            val jsonString = gson.toJson(dataList)

            // JSON 형식의 데이터를 다시 역직렬화하여 리스트로 변환
            val typeToken = object : TypeToken<List<PieEntry>>() {}.type
            val copiedList = gson.fromJson<List<PieEntry>>(jsonString, typeToken)

            // 내림차순으로 정렬
            val sortedList = copiedList.sortedByDescending { it.value }

            // 정렬된 데이터 중에서 인덱스 0부터 2까지 추출
            val selectedDataList = sortedList.subList(0, 3)

            bestItem1.text = "1st: ${selectedDataList[0].label} -> ${selectedDataList[0].value}"
            bestItem2.text = "2nd: ${selectedDataList[1].label} -> ${selectedDataList[1].value}"
            bestItem3.text = "3rd: ${selectedDataList[2].label} -> ${selectedDataList[2].value}"
        }
    }
}