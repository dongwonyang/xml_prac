package com.example.xml_prac

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Path
import android.graphics.PointF
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import com.example.xml_prac.databinding.ActivityLineChartBinding
import kotlinx.coroutines.flow.Flow

class LineChartActivity : AppCompatActivity() {
    private val binding: ActivityLineChartBinding by lazy {
        ActivityLineChartBinding.inflate(layoutInflater)
    }

    val pointList by lazy {
        listOf(
            binding.ivMon,
            binding.ivTue,
            binding.ivWed,
            binding.ivThu,
            binding.ivFri,
            binding.ivSat,
            binding.ivSun,
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        initView()
    }

    private fun initView() = with(binding) {
        val criteria = 10000

        val dataList = listOf(
            criteria - 5000,
            criteria + 3000,
            criteria + 8000,
            criteria - 7000,
            criteria
        )

        // 가장 큰 차이를 구합니다.
        val maxDiff = dataList.maxOf { Math.abs(it - criteria) }

        // 우선 모든 ImageView를 INVISIBLE로 설정하여 기본적으로 보이지 않도록 함
        pointList.forEach {
            it.visibility = View.INVISIBLE
        }

        // 각 데이터에 대해 계산 후 적용
        dataList.forEachIndexed { index, data ->
            val imageView = pointList.getOrNull(index) ?: return@forEachIndexed

            // 기준점과의 차이 비율 계산 (기준점보다 높으면 +, 낮으면 -)
            val diff = data - criteria
            val scale = diff.toFloat() / maxDiff

            // 차이에 비례하여 margin 값을 계산합니다.
            val margin = (scale * 200).toInt()  // 200은 margin 조정 범위

            // margin이 0보다 크면 bottomMargin, 아니면 topMargin을 설정
            val layoutParams = imageView.layoutParams as ViewGroup.MarginLayoutParams
                if (margin > 0) {
                    layoutParams.bottomMargin = margin
                    layoutParams.topMargin = 0
                } else {
                    layoutParams.topMargin = -margin  // 음수로 설정하여 위쪽에 margin을 추가
                    layoutParams.bottomMargin = 0
                }
                imageView.layoutParams = layoutParams
                imageView.visibility = View.VISIBLE
        }

        // 커스텀 뷰 생성하여 선 그리기
        val lineView = LineChartView(this@LineChartActivity)
        binding.root.addView(lineView)
    }

    inner class LineChartView(context: Context) : View(context) {
        init {
            // View의 elevation 값을 낮춰서 다른 위젯들보다 아래로 그려지도록 함
            elevation = -1f
        }
        private val paint = Paint().apply {
            color = Color.GRAY
            strokeWidth = 5f  // 선 두께
            isAntiAlias = true
        }

        override fun onDraw(canvas: Canvas) {
            super.onDraw(canvas)

            val points = mutableListOf<PointF>()

            for(i in 0 until pointList.size - 1){
                if(pointList[i].visibility == View.VISIBLE && pointList[i+1].visibility == View.VISIBLE){
                    val location = IntArray(2)

                    // 첫 번째 점의 위치 계산
                    pointList[i].let {
                        it.getLocationOnScreen(location)
                        val x = location[0] + it.width / 2f
                        val y = location[1] + it.height / 2f - 90f
                        points.add(PointF(x, y + 10 ))
                        points.add(PointF(x, y - 10 ))
                    }

                    // 두 번째 점의 위치 계산
                    pointList[i + 1].let {
                        it.getLocationOnScreen(location)
                        val x = location[0] + it.width / 2f
                        val y = location[1] + it.height / 2f - 90f
                        points.add(PointF(x, y - 10 ))
                        points.add(PointF(x, y + 10 ))

                    }

                    val path = Path()
                    path.moveTo(points[0].x, points[0].y)
                    for (i in 1 until points.size) {
                        path.lineTo(points[i].x, points[i].y)
                    }
                    canvas.drawPath(path, paint)

                    points.clear()
                }
            }
        }
    }
}


