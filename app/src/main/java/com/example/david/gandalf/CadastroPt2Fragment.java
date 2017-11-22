package com.example.david.gandalf;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.example.david.gandalf.helpers.CadastroHelper;
import com.example.david.gandalf.models.Cliente;
import com.example.david.gandalf.models.Endereco;

/**
 * A simple {@link Fragment} subclass.
 */
public class CadastroPt2Fragment extends Fragment {

    private Button btnNext;
    private EditText campCep;
    private EditText campEndereco;
    private EditText campNumero;
    private EditText campBairro;
    private EditText campCidade;

    public CadastroPt2Fragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.activity_cadastro_pt2, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        btnNext = (Button) getActivity().findViewById(R.id.cadastro_proximo_pt2);
        campCep = (EditText) getActivity().findViewById(R.id.cadastro_cep);
        campEndereco = (EditText) getActivity().findViewById(R.id.cadastro_endereco);
        campNumero = (EditText) getActivity().findViewById(R.id.cadastro_numero);
        campCidade = (EditText) getActivity().findViewById(R.id.cadastro_cidade);

        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            CadastroActivity cadastroActivity = (CadastroActivity) getActivity();
            Cliente cliente = cadastroActivity.getCliente();
            Endereco endereco = cadastroActivity.getEndereco();

            endereco.setCEPEndereco(campCep.getText().toString());
            endereco.setNomeEndereco(campEndereco.getText().toString());
            endereco.setNumeroEndereco(campNumero.getText().toString());
            endereco.setCidadeEndereco(campCidade.getText().toString());

            new CadastroHelper((CadastroActivity) getActivity()).chamaFragment(new CadastroPt3Fragment());
            }
        });
        super.onActivityCreated(savedInstanceState);
    }

}
