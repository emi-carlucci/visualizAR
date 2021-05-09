package edu.ort.visualizar.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Spinner
import android.widget.TextView
import edu.ort.visualizar.R

import com.google.android.material.snackbar.Snackbar

class EditarIndicadorFragment : Fragment() {

    lateinit var v : View
    lateinit var btnConfirm : Button
    lateinit var btnRestablecer : Button
    lateinit var inputTitulo : TextView
    lateinit var inputDescripcion : TextView
    lateinit var inputFormula : TextView
    lateinit var inputFrec : TextView
    lateinit var spinner : Spinner
    var validarAction : Boolean = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        v = inflater.inflate(R.layout.fragment_editar_indicador, container, false)
        btnConfirm = v.findViewById(R.id.btnConfirmar)
        btnRestablecer = v.findViewById(R.id.btnRestablecer)
        inputTitulo = v.findViewById(R.id.inputIndicadorTitulo)
        inputDescripcion = v.findViewById(R.id.inputIndicadorDescripcion)
        inputFormula = v.findViewById(R.id.inputFormula)
        inputFrec = v.findViewById(R.id.inputFrecuencia)
        return v
    }

    override fun onStart() {
        super.onStart()

        btnConfirm.setOnClickListener{
            if(inputTitulo.text.toString().equals("")) {
                Snackbar.make(v, "Ingrese el título.", Snackbar.LENGTH_SHORT).show()
                validarAction = false
            }
            if(inputDescripcion.text.toString().equals("")){
                Snackbar.make(v, "Ingrese la descripción.", Snackbar.LENGTH_SHORT).show()
                validarAction = false
            }
            if(validarAction){
                // Aca se persiste en la BD
                println("GRABANDO....")
                Thread.sleep(1000)
                println("vuelvo a HOME")
                // val action = Indicador_EditarDirections.action_indicador_Editar_to_home2()

                // v.findNavController().navigate(action)
            }
            validarAction=true
        }
        btnRestablecer.setOnClickListener { // Aca deberia volver a la version anterior (la original que se estaba editando)
            inputTitulo.setText("")
            inputDescripcion.setText("")
            inputFormula.setText("")
            inputFrec.setText("")
        }
    }
}