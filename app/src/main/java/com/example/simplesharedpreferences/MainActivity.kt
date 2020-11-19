package com.example.simplesharedpreferences

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), View.OnClickListener {

    companion object {
        private val REQUEST_CODE = 100
    }

    private lateinit var mUserPreference: UserPreference

    private var isPreferenceEmpty = false
    private lateinit var userModel: UserModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        supportActionBar?.title = "My User Preference"

        mUserPreference = UserPreference(this)

        showExistingPreference()

        btn_save.setOnClickListener(this)
    }

    override fun onClick(p0: View) {
        if (p0.id == R.id.btn_save) {
            val intent = Intent(this, FormUserPreferenceActivity::class.java)
            when {
                isPreferenceEmpty -> {
                    intent.putExtra(FormUserPreferenceActivity.EXTRA_TYPE_FORM, FormUserPreferenceActivity.TYPE_ADD)
                    intent.putExtra("USER", userModel)
                }
                else -> {
                    intent.putExtra(FormUserPreferenceActivity.EXTRA_TYPE_FORM, FormUserPreferenceActivity.TYPE_EDIT)
                    intent.putExtra("USER", userModel)
                }
            }
            startActivityForResult(intent, REQUEST_CODE)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_CODE) {
            if (resultCode == FormUserPreferenceActivity.RESULT_CODE) {
                userModel = data?.getParcelableExtra<UserModel>(FormUserPreferenceActivity.EXTRA_RESULT) as UserModel
                populateView(userModel)
                checkForm(userModel)
            }
        }
    }

    private fun showExistingPreference() {
        userModel = mUserPreference.getUser()
        populateView(userModel)
        checkForm(userModel)
    }

    private fun populateView(userModel: UserModel) {
        tv_name.text = if (userModel.name.toString().isEmpty()) "Tidak ada" else userModel.name
        tv_age.text = if (userModel.age.toString().isEmpty()) "Tidak ada" else userModel.age.toString()
        tv_is_love_milan.text = if (userModel.isLove) "Ya" else "Tidak"
        tv_email.text = if (userModel.email.toString().isEmpty()) "Tidak Ada" else userModel.email
        tv_phone.text = if (userModel.phoneNumber.toString().isEmpty()) "Tidak Ada" else userModel.phoneNumber

    }

    private fun checkForm(userModel: UserModel) {
        when {
            userModel.name.toString().isNotEmpty() -> {
                btn_save.text = getString(R.string.change)
                isPreferenceEmpty = false
            }
            else -> {
                btn_save.text = getString(R.string.save)
                isPreferenceEmpty = true
            }
        }
    }
}