import android.content.Context
import android.graphics.Canvas
import android.graphics.drawable.Drawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.AttributeSet
import android.util.Log
import android.view.Gravity
import android.view.MotionEvent
import android.view.View
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.widget.AppCompatButton
import androidx.appcompat.widget.AppCompatEditText
import androidx.core.content.ContextCompat
import com.example.storyapp.EmailEditText
import com.example.storyapp.LogButton
import com.example.storyapp.PasswordEditText
import com.example.storyapp.R

class RegisterActivity : AppCompatActivity() {
    private lateinit var myButton: LogButton
    private lateinit var nameEditText: EditText
    private lateinit var emailEditText: EmailEditText
    private lateinit var passwordEditText: PasswordEditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        myButton = findViewById(R.id.login_button)
        nameEditText = findViewById(R.id.name_edit_text)
        emailEditText = findViewById(R.id.email_edit_text)
        passwordEditText = findViewById(R.id.password_edit_text)
        setMyButtonEnable()

        emailEditText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {
            }
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                if (passwordEditText.text!!.isNotEmpty() && nameEditText.text!!.isNotEmpty()) {
                    setMyButtonEnable()
                }
            }
            override fun afterTextChanged(s: Editable) {
            }
        })

        passwordEditText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {
            }
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                if (emailEditText.text!!.isNotEmpty()  && nameEditText.text!!.isNotEmpty()) {
                    setMyButtonEnable()
                }
            }
            override fun afterTextChanged(s: Editable) {
            }
        })

        nameEditText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {
            }
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                if (emailEditText.text!!.isNotEmpty()  && passwordEditText.text!!.isNotEmpty()) {
                    setMyButtonEnable()
                }
            }
            override fun afterTextChanged(s: Editable) {
            }
        })

        myButton.setOnClickListener {
            Toast.makeText(this@RegisterActivity, emailEditText.text, Toast.LENGTH_SHORT).show()
            emailEditText.text?.clear()
        }
    }

    private fun setMyButtonEnable() {
        val result = emailEditText.text
        myButton.isEnabled = result != null && result.toString().isNotEmpty()
    }
}

class LoginButton : AppCompatButton {

    private lateinit var enabledBackground: Drawable
    private lateinit var disabledBackground: Drawable
    private var txtColor: Int = 0

    constructor(context: Context) : super(context) {
        init()
    }
    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        init()
    }
    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        init()
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        background = if(isEnabled) enabledBackground else disabledBackground
        setTextColor(txtColor)
        textSize = 12f
        gravity = Gravity.CENTER
        text = context.getString(R.string.register)
    }

    private fun init() {
        txtColor = ContextCompat.getColor(context, android.R.color.background_light)
        enabledBackground = ContextCompat.getDrawable(context, R.drawable.bg_button) as Drawable
        disabledBackground = ContextCompat.getDrawable(context, R.drawable.bg_button_disable) as Drawable
    }
}