package com.example.expensenote_11

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {

    //สร้างตัวแปรสำหรับหน้า UI Element
    private lateinit var etAmount: EditText
    private lateinit var etNote: EditText
    private lateinit var btnAdd: Button
    private lateinit var btnClear: Button
    private lateinit var tvLastItem: TextView
    private lateinit var tvTotal: TextView

    //สร้างตัวแปรสำหรับการเก็บค่ายอดรวม
    private var total: Double = 0.0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        etAmount = findViewById(R.id.etAmount)
        etNote = findViewById(R.id.etNote)
        btnAdd = findViewById(R.id.btnAdd)
        btnClear = findViewById(R.id.btnClear)
        tvLastItem = findViewById(R.id.tvLastItem)
        tvTotal = findViewById(R.id.tvTotal)

        btnAdd.setOnClickListener {
            //ดึงข้อความจากช่องกรอกจำนวนเงิน และช่องหมายเหตุ พร้อมตัดช่องว่างออก
            val amountText = etAmount.text.toString().trim()
            val noteText = etNote.text.toString().trim()

            //ตรวจสอบเงื่อนไข: ถ้าช่องกรอกจำนวนเงินว่าง ให้แสดงคำเตือน
            if (amountText.isEmpty()) {
                Toast.makeText(this, "กรุณากรอกจำนวนเงิน", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val amount = amountText.toDoubleOrNull()
            if (amount == null) {
                Toast.makeText(this, "กรุณากรอกจำนวนเงินเป็นตัวเลข", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            total += amount

            val showNote = noteText.ifEmpty { "ไม่ระบุ" }

            tvLastItem.text = "รายการล่าสุด: $showNote - %.2f บาท".format(amount)
            tvTotal.text = "ยอดรวมทั้งหมด: %.2f บาท".format(total)
        }

        btnClear.setOnClickListener {
            total = 0.0

            etAmount.text.clear()
            etNote.text.clear()

            tvLastItem.text = "รายการล่าสุด: -"
            tvTotal.text = "ยอดรวมทั้งหมด: 0.0 บาท"
        }
    }
}
