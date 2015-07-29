package br.com.williamsilva.economizze.view.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.melnykov.fab.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

import br.com.williamsilva.economizze.DAO.DespesaDAO;
import br.com.williamsilva.economizze.R;
import br.com.williamsilva.economizze.controller.adapters.AdapterDespesaListView;
import br.com.williamsilva.economizze.library.SwipeDismissListViewTouchListener;
import br.com.williamsilva.economizze.model.Despesa;
import br.com.williamsilva.economizze.view.DespesaActivity;
import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import butterknife.OnItemClick;

/**
 * Created by William on 19/11/2014.
 */
public class DespesaFragment extends Fragment {

    @InjectView(R.id.lista_despesa)
    protected ListView lista;

    @InjectView(R.id.fabDespesa)
    protected FloatingActionButton fab;

    private View view;

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        this.view = inflater.inflate(R.layout.fragment_despesas, container, false);
        ButterKnife.inject(DespesaFragment.this,this.view);
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

        return this.view;
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

    @OnItemClick(R.id.lista_despesa)
    protected void onClickLista(int position) {
        Despesa despesa = new DespesaDAO(view.getContext()).listarDespesa().get(position);
        Intent intent = new Intent(view.getContext(), DespesaActivity.class);
        intent.putExtra("DESPESA",despesa);
        startActivity(intent);
    }

    @Override
    public void onResume() {
        super.onResume();
        carregarListaDespesa();
    }

   public void carregarListaDespesa()
    {
        List<Despesa> despesas = new DespesaDAO(view.getContext()).listarDespesa();
        lista.setAdapter(new AdapterDespesaListView(despesas,getActivity()));
    }

    public void excluir(Long id){
        new DespesaDAO(view.getContext()).excluirDespesa(id);
        carregarListaDespesa();
    }

    @OnClick(R.id.fabDespesa)
    public void adicionarDespesa(View view){
        startActivity(new Intent(view.getContext(),DespesaActivity.class));
    }


}
