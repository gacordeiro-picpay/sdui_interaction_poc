package pic.poc.sdui.interaction.example.presentation

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.textfield.TextInputLayout.BOX_BACKGROUND_FILLED
import com.google.android.material.textfield.TextInputLayout.BOX_BACKGROUND_OUTLINE
import pic.poc.sdui.interaction.R
import pic.poc.sdui.interaction.databinding.ExampleActivityBinding
import pic.poc.sdui.interaction.databinding.ViewButtonBinding
import pic.poc.sdui.interaction.databinding.ViewDividerBinding
import pic.poc.sdui.interaction.databinding.ViewEditBinding
import pic.poc.sdui.interaction.databinding.ViewSwitchBinding
import pic.poc.sdui.interaction.databinding.ViewTextBinding
import pic.poc.sdui.interaction.sdui.domain.UiButton
import pic.poc.sdui.interaction.sdui.domain.UiComponent
import pic.poc.sdui.interaction.sdui.domain.UiDivider
import pic.poc.sdui.interaction.sdui.domain.UiEdit
import pic.poc.sdui.interaction.sdui.domain.UiSwitch
import pic.poc.sdui.interaction.sdui.domain.UiText

class ExampleActivity : AppCompatActivity() {

    private lateinit var viewModel: ExampleViewModel
    private lateinit var binding: ExampleActivityBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ExampleActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this)[ExampleViewModel::class.java]
        viewModel.state.observe(this, ::handleState)
    }

    private fun handleState(state: ExampleViewState) {
        binding.container.removeAllViews()
        state.components.forEach(::addView)
    }

    private fun addView(component: UiComponent) = when (component) {
        is UiDivider -> addDivider()
        is UiText -> addViewFor(component)
        is UiButton -> addViewFor(component)
        is UiEdit -> addViewFor(component)
        is UiSwitch -> addViewFor(component)
        else -> log("Invalid UiComponent: $component")
    }

    private fun addDivider() {
        val view = ViewDividerBinding.inflate(layoutInflater).root
        binding.container.addView(view)
    }

    private fun addViewFor(component: UiText) {
        val view = ViewTextBinding.inflate(layoutInflater).root
        view.text = component.text
        view.setTextAppearance(getStyleFor(component))
        binding.container.addView(view)
    }

    private fun getStyleFor(component: UiText) = when (component.style) {
        "title" -> R.style.TextTitle
        else -> R.style.TextBody
    }

    private fun addViewFor(component: UiButton) {
        val view = ViewButtonBinding.inflate(layoutInflater).root
        view.text = component.text
        view.setBackgroundColor(getColorFor(component))
        view.setOnClickListener { viewModel.onButtonClicked(component) }
        binding.container.addView(view)
    }

    private fun getColorFor(component: UiButton) = when (component.style) {
        "primary" -> resources.getColor(R.color.teal_200, theme)
        else -> resources.getColor(R.color.purple_200, theme)
    }

    private fun addViewFor(component: UiEdit) {
        val layout = ViewEditBinding.inflate(layoutInflater)
        layout.root.boxBackgroundMode = getBackgroundFor(component)
        layout.edit.setText(component.text)
        layout.edit.afterTextChanged { viewModel.afterTextChanged(component, it) }
        binding.container.addView(layout.root)
    }

    private fun getBackgroundFor(component: UiEdit) = when (component.style) {
        "filled" -> BOX_BACKGROUND_FILLED
        else -> BOX_BACKGROUND_OUTLINE
    }

    private fun addViewFor(component: UiSwitch) {
        val view = ViewSwitchBinding.inflate(layoutInflater).root
        view.isChecked = component.isChecked
        view.setOnCheckedChangeListener { _, check -> viewModel.onSwitchToggled(component, check) }
        binding.container.addView(view)
    }

    private fun log(message: String) {
        Log.d("DebugTag", message)
    }
}

/**
 * Extension function to simplify setting an afterTextChanged action to EditText components.
 */
fun EditText.afterTextChanged(afterTextChanged: (String) -> Unit) {
    this.addTextChangedListener(object : TextWatcher {
        override fun afterTextChanged(editable: Editable?) {
            afterTextChanged.invoke(editable.toString())
        }

        override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}

        override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}
    })
}