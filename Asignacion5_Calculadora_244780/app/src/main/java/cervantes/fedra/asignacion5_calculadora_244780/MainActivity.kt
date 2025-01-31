package cervantes.fedra.asignacion5_calculadora_244780

import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {
    private lateinit var tvNum1: TextView
    private lateinit var tvNum2: TextView
    private lateinit var imageView: ImageView

    private var currentInput: String = ""
    private var operator: String? = null
    private var firstOperand: Double? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        // Inicializar los elementos de la interfaz
        tvNum1 = findViewById(R.id.tv_num1)
        tvNum2 = findViewById(R.id.tv_num2)
        imageView = findViewById(R.id.imageView)


        imageView.setImageResource(R.mipmap.konig)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }


        val buttons = listOf(
            R.id.btn0, R.id.btn1, R.id.btn2, R.id.btn3, R.id.btn4,
            R.id.btn5, R.id.btn6, R.id.btn7, R.id.btn8, R.id.btn9,
            R.id.btnPunto
        )

        for (id in buttons) {
            findViewById<Button>(id).setOnClickListener { onNumberClick(it as Button) }
        }

        // Configura botones de operadores
        findViewById<Button>(R.id.btnMas).setOnClickListener { onOperatorClick(it as Button) }
        findViewById<Button>(R.id.btnMenos).setOnClickListener { onOperatorClick(it as Button) }
        findViewById<Button>(R.id.btnPor).setOnClickListener { onOperatorClick(it as Button) }
        findViewById<Button>(R.id.btnDividir).setOnClickListener { onOperatorClick(it as Button) }

        // Botones de igual y limpiar
        findViewById<Button>(R.id.btnIgual).setOnClickListener { onEqualClick() }
        findViewById<Button>(R.id.button).setOnClickListener { onClearClick() }
    }

    private fun onNumberClick(button: Button) {
        currentInput += button.text
        tvNum1.text = currentInput
    }

    private fun onOperatorClick(button: Button) {
        operator = button.text.toString()
        firstOperand = currentInput.toDoubleOrNull()
        currentInput = ""
        tvNum1.text = "$firstOperand $operator"
    }

    private fun onEqualClick() {
        val secondOperand = currentInput.toDoubleOrNull()
        if (firstOperand != null && secondOperand != null && operator != null) {
            val result = when (operator) {
                "+" -> firstOperand!! + secondOperand
                "-" -> firstOperand!! - secondOperand
                "x" -> firstOperand!! * secondOperand
                "/" -> {
                    if (secondOperand != 0.0) {
                        firstOperand!! / secondOperand
                    } else {
                        tvNum2.text = "Error"
                        return
                    }
                }
                else -> return
            }
            tvNum2.text = result.toString()
            tvNum1.text = "$firstOperand $operator $secondOperand"
            currentInput = result.toString()
            firstOperand = null
            operator = null
        }
    }

    private fun onClearClick() {
        currentInput = ""
        operator = null
        firstOperand = null
        tvNum1.text = "0.0"
        tvNum2.text = "0.0"
    }
}
