package com.picpay.desafio.android.util

import android.app.Activity
import android.content.Context
import android.content.DialogInterface
import android.graphics.Typeface
import android.text.Spannable
import android.text.SpannableString
import android.text.style.StyleSpan
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.widget.NestedScrollView
import com.google.android.material.floatingactionbutton.FloatingActionButton


object ViewUtils {
    private var mToast: Toast? = null

    fun toast(context: Context?, text: String?, length: Int) {
        mToast?.cancel()

        mToast = Toast.makeText(context, text, length)
        mToast?.show()
    }

    fun toast(context: Context, resString: Int, length: Int) {
        toast(context, context.getString(resString), length)
    }

    fun dialog(context: Context, mensagem: String?): AlertDialog.Builder? {
        return AlertDialog.Builder(context)
            .setMessage(mensagem)
            .setCancelable(false)

    }

    fun dialogInformativo(context: Context, mensagem: String?) {
        return dialog(context, mensagem)
            ?.setPositiveButton(
                "OK"
            ) { dialog: DialogInterface, _: Int -> dialog.dismiss() }?.create()
            ?.show()!!
    }

    fun dialogSimNao(
        context: Context,
        mensagem: String?,
        listenerSim: DialogInterface.OnClickListener?,
        listenerNao: DialogInterface.OnClickListener?
    ) {

        return dialog(context, mensagem)
            ?.setOnDismissListener(null)
            ?.setNegativeButton("NÃO", listenerNao)
            ?.setPositiveButton("SIM", listenerSim)
            ?.create()
            ?.show()!!

    }


     fun pageUp(fab: FloatingActionButton, nestedScroll: NestedScrollView) {
        nestedScroll.setOnScrollChangeListener { v: NestedScrollView, _: Int, scrollY: Int, _: Int, oldScrollY: Int ->
            if(scrollY >60){
                if (v.getChildAt(v.childCount - 1) != null) {
                    if (scrollY <= v.getChildAt(v.childCount - 1).measuredHeight - v.measuredHeight && scrollY < oldScrollY
                    ) {
                        fab.hide()
                    } else {
                        fab.show()
                    }
                }
            }else{
                fab.hide()
            }

        }

        fab.setOnClickListener {
            nestedScroll.scrollTo(0, 0)
        }
    }

    fun showKeyboard(activity: Activity?) {
        if (activity == null) return
        val keyboard = activity.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        val view = activity.currentFocus
        if (view != null) {
            keyboard.showSoftInput(view, 0)
        }
    }

    fun hideKeyboard(activity: Activity?) {
        if (activity == null) return
        val keyboard = activity.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        val view = activity.currentFocus
        if (view != null) {
            keyboard.hideSoftInputFromWindow(view.windowToken, 0)
        }
    }

    fun setBold(frase: String, posicaoInicial: Int, posicaoFinal: Int): SpannableString {
        val str = SpannableString(frase)
        str.setSpan(
            StyleSpan(Typeface.BOLD),
            posicaoInicial,
            posicaoFinal,
            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
        )
        return str
    }


}