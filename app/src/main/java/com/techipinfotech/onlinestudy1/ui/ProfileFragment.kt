package com.techipinfotech.onlinestudy1.ui

import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.view.*
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.techipinfotech.onlinestudy1.API
import com.techipinfotech.onlinestudy1.LoginActivity
import com.techipinfotech.onlinestudy1.LoginApi
import com.techipinfotech.onlinestudy1.R
import com.techipinfotech.onlinestudy1.databinding.FragmentProfileBinding
import com.techipinfotech.onlinestudy1.model.Received
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import technited.minds.androidutils.ProcessDialog
import technited.minds.androidutils.SharedPrefs


class ProfileFragment : Fragment() {


    private lateinit var userSharedPreferences: SharedPrefs
    private lateinit var binding: FragmentProfileBinding
    private lateinit var changePasswordDialog: Dialog
    private lateinit var processDialog: ProcessDialog
    private lateinit var animation: Animation

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_profile, container, false)

        processDialog = ProcessDialog(context)
        userSharedPreferences = SharedPrefs(context, "USER")

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        changePasswordDialog = Dialog(requireContext())
        changePasswordDialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        changePasswordDialog.setContentView(R.layout.dialog_change_password)
        val window: Window? = changePasswordDialog.window
        window?.setLayout(
            WindowManager.LayoutParams.MATCH_PARENT,
            WindowManager.LayoutParams.MATCH_PARENT
        )
        window?.clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND)
        window?.setBackgroundDrawableResource(R.color.semi_transparent)
        changePasswordDialog.setCancelable(true)


        binding.apply {

            fullName.text = userSharedPreferences.get("student_name")
            mobile.text = userSharedPreferences.get("student_mobile")
            className.text = userSharedPreferences.get("class_name")
            admissionDate.text = userSharedPreferences.get("admission_date")
            admissionExpire.text = userSharedPreferences.get("admission_last_date")
            email.text = userSharedPreferences.get("student_email")


            val url: String = API.STUDENT.toString() + userSharedPreferences.get("photo")
            Glide
                .with(requireContext())
                .load(url)
                .centerCrop()
                .placeholder(R.drawable.ic_user)
                .into(profileImage)

            animation = AnimationUtils.loadAnimation(context, R.anim.fade_in)
            profileImage.startAnimation(animation)

            logoutButton.setOnClickListener {
                userSharedPreferences.clearAll()
                val i = Intent(context, LoginActivity::class.java)
                startActivity(i)
                activity?.finish()
            }


            changeButton.setOnClickListener {
                changePasswordDialog.show()
                val old_pass = changePasswordDialog.findViewById(R.id.old_password) as EditText
                val new_pass = changePasswordDialog.findViewById(R.id.new_password) as EditText
                val cnf_pass = changePasswordDialog.findViewById(R.id.cnf_password) as EditText
                val submit = changePasswordDialog.findViewById(R.id.submit) as Button

                submit.setOnClickListener {
                    if (userSharedPreferences.get("student_password") == old_pass.text.toString() && old_pass.text.toString() != "") {
                        if (cnf_pass.text.toString() == new_pass.text.toString()  && new_pass.text.toString() != "" && cnf_pass.text.toString() != "") {
                            changePassword(
                                userSharedPreferences.get("student_mobile"),
                                new_pass.text.toString()
                            )
                        } else Toast.makeText(context, "Password not Matched", Toast.LENGTH_SHORT)
                            .show()
                    } else
                        Toast.makeText(context, "Invalid Old Password", Toast.LENGTH_SHORT).show()


                }

            }
        }
    }

    private fun changePassword(username: String, password: String) {
        processDialog.show()
        val change = LoginApi.getApiService().changePassword(username, password)
        change.enqueue(object : Callback<Received> {
            override fun onResponse(call: Call<Received>, response: Response<Received>) {

                val received = response.body()
                Toast.makeText(context, received?.status, Toast.LENGTH_SHORT).show()
                userSharedPreferences.set("student_password", password)
                processDialog.dismiss()
                changePasswordDialog.dismiss()
            }

            override fun onFailure(call: Call<Received>, t: Throwable) {
                Toast.makeText(context, "Error", Toast.LENGTH_SHORT).show()
                processDialog.dismiss()
            }
        })
    }

}
