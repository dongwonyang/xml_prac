package com.example.xml_prac

import android.graphics.Color
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.xml_prac.databinding.ActivityMpLineChartBinding
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet

class MpLineChartActivity : AppCompatActivity() {
    private val binding by lazy {
        ActivityMpLineChartBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        // 데이터를 정의
        val entries = listOf(
            Entry(0f, 10f),
            Entry(1f, 20f),
            Entry(2f, 15f),
            Entry(3f, 30f),
            Entry(4f, 10f),
            Entry(5f, 20f),
            Entry(6f, 15f),
        )

        // 아이콘 이미지 정의
        val icons = listOf(
            ResourcesCompat.getDrawable(resources, R.drawable.ic_point, null),
            ResourcesCompat.getDrawable(resources, R.drawable.ic_point, null),
            ResourcesCompat.getDrawable(resources, R.drawable.ic_point, null),
            ResourcesCompat.getDrawable(resources, R.drawable.ic_point, null)
        )

        entries.forEachIndexed { index, entry ->
            entry.icon = icons.getOrNull(index)
        }

        // LineDataSet 생성
        val dataSet = LineDataSet(entries, "Label").apply {
            // 선의 색, 두께 설정
            color = Color.GRAY
            lineWidth = 2f

            setDrawValues(false)
            setDrawIcons(true)

            circleRadius = 5.0F
            setCircleColor(ContextCompat.getColor(this@MpLineChartActivity, R.color.black))
            circleHoleRadius = 1.0F
            circleHoleColor = ContextCompat.getColor(this@MpLineChartActivity, R.color.white)
        }

        // 데이터 설정
        val lineData = LineData(dataSet)

        // 차트에 데이터 설정
        binding.chart.run {
            data = lineData
            setBackgroundColor(Color.WHITE)
            invalidate()  // 차트 새로 그리기


            description.isEnabled = false
            legend.isEnabled = false

            xAxis.run {
                setDrawGridLines(false)
                setDrawAxisLine(false)
                axisLineColor = Color.TRANSPARENT
                textColor = Color.TRANSPARENT
            }
            axisLeft.apply {
                setDrawGridLines(false)
                setDrawAxisLine(false)
                axisLineColor = Color.TRANSPARENT
                textColor = Color.TRANSPARENT
            }
            axisRight.apply {
                setDrawGridLines(false)
                setDrawAxisLine(false)
                axisLineColor = Color.TRANSPARENT
                textColor = Color.TRANSPARENT
            }
        }
    }

}