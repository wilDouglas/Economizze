package br.com.williamsilva.economizze.view.fragments;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.melnykov.fab.FloatingActionButton;

import java.util.List;

import br.com.williamsilva.economizze.DAO.ReceitaDAO;
import br.com.williamsilva.economizze.R;
import br.com.williamsilva.economizze.controller.adapters.AdapterDespesaListView;
import br.com.williamsilva.economizze.controller.adapters.AdapterReceitaListView;
import br.com.williamsilva.economizze.library.SwipeDismissListViewTouchListener;
import br.com.williamsilva.economizze.model.Receita;
import br.com.williamsilva.economizze.view.ReceitaActivity;
import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import butterknife.OnItemClick;

public class ReceitaFragment extends Fragment {

    @InjectView(R.id.lista_receita)
    protected ListView lista;
    @InjectView(R.id.fabReceita)
    protected FloatingActionButton fab;

    private View view;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_receitas, container, false);
        ButterKnife.inject(ReceitaFragment.this,view);
        fab.attachToListView(lista);
        this.SwipeIntefaceExcluir();

        lista.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
               if(scrollState == 0 ) {
                   fab.show();
               }
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                if(firstVisibleItem != 0){
                    fab.hide();
                }
            }
        });

        return view;
    }

    @OnItemClick(R.id.lista_receita)
    protected void onCilickLista(int position) {
                Receita receita = new ReceitaDAO(view.getContext()).listarReceitas().get(position);
                Intent intent = new Intent(view.getContext(), ReceitaActivity.class);
                intent.putExtra("RECEITA",receita);
                startActivity(intent);
    }

    private void SwipeIntefaceExcluir() {

        SwipeDismissListViewTouchListener touchListener =
// é ultilizado quando o usuário passar o dedo  da esquerda para direita ou da direita para esquerda
                new SwipeDismissListViewTouchListener(
                        lista,
                        new SwipeDismissListViewTouchListener.DismissCallbacks() {
                            @Override
                            public boolean canDismiss(int position) {
                                return true;
                            }

                            @Override
                            public void onDismiss(ListView listView, int[] reverseSortedPositions) {
                                try {
                                    for (int position : reverseSortedPositions) {
                                        //recupero o id da despesa ou receita e solicito a exclusao do mesmo
                                        excluir(listView.getItemIdAtPosition(position));
                                    }
                                    // ao finalizar a exclusão o mesmo apresentará uma menssagem
                                    Toast.makeText(view.getContext(), getString(R.string.excluido_com_sucesso), Toast.LENGTH_SHORT).show();
                                }catch (Exception erro){
                                    Toast.makeText(view.getContext(),getString(R.string.erro_sistema),Toast.LENGTH_LONG).show();
                                }

                            }
                        });

        lista.setOnTouchListener(touchListener);
        lista.setOnScrollListener(touchListener.makeScrollListener());
    }

    @Override
    public void onResume() {
        super.onResume();
        carregarListaReceitas();
    }

    private  void excluir(Long id){
        new ReceitaDAO(view.getContext()).excluirReceita(id);
        carregarListaReceitas();
    }

    private void carregarListaReceitas() {
        List<Receita> receitas = new ReceitaDAO(view.getContext()).listarReceitas();
        lista.setAdapter(new AdapterReceitaListView(receitas,getActivity()));
    }

    @OnClick(R.id.fabReceita)
    public void adicionarReceita(View view){
        startActivity(new Intent(view.getContext(),ReceitaActivity.class));
    }


}

