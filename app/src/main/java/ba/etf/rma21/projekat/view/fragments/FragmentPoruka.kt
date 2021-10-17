package ba.etf.rma21.projekat.view.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import ba.etf.rma21.projekat.R

class FragmentPoruka(var poruka:String) : Fragment() {
    private lateinit var textView: TextView


    @SuppressLint("SetTextI18n")
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_poruka, container, false)

        textView = view.findViewById(R.id.tvPoruka)


        textView.text = poruka

        return view
    }

    companion object { fun newInstance(poruka: String): FragmentPoruka = FragmentPoruka(poruka) }

}