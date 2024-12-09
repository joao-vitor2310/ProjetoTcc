/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.projeto.dao;

import br.com.projeto.jbdc.ConnectionFactory;
import br.com.projeto.model.Fornecedores;
import br.com.projeto.model.Produtos;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;

/**
 *
 * @author joaov
 */
public class ProdutosDAO {

    private Connection con;

    public ProdutosDAO() {
        this.con = new ConnectionFactory().getConnection();
    }

    //Metodo para cadastrar os produtos
    public void cadastrar(Produtos obj) {
        try {
            String sql = "insert into tb_produtos (descricao, preco, qtd_estoque,for_id, data_compra) values(?,?,?,?,?)";

            //conectar o banco de dados e organizar o comando SQL
            PreparedStatement stmt = con.prepareStatement(sql);
            
            stmt.setString(1, obj.getDescricao());
            stmt.setDouble(2, obj.getPreco());
            stmt.setInt(3, obj.getQtd_estoque());
            stmt.setInt(4, obj.getFornecedor().getId());
            stmt.setString(5, obj.getData_compra());

            stmt.execute();
            stmt.close();

            JOptionPane.showMessageDialog(null, "Produto cadastrado com Sucesso!");

        } catch (Exception erro) {
            JOptionPane.showMessageDialog(null, "Erro :" + erro);
        }
    }
    
    //metodo para alterar produtos
    public void alterar(Produtos obj) {
        try {
            
            String sql = "update tb_produtos set descricao=?, preco=?, qtd_estoque=?, for_id=? where id=?";

            //conectar o banco de dados e organizar o comando SQL
            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.setString(1, obj.getDescricao());
            stmt.setDouble(2, obj.getPreco());
            stmt.setInt(3, obj.getQtd_estoque());
            
            stmt.setInt(4, obj.getFornecedor().getId());
            stmt.setInt(5, obj.getId());
            

            stmt.execute();
            stmt.close();

            JOptionPane.showMessageDialog(null, "Produto alterado com Sucesso!");

        } catch (Exception erro) {
            JOptionPane.showMessageDialog(null, "Erro :" + erro);
        }
    }
    
    //metodo para excluir os produtos
    public void excluir(Produtos obj){
        try {
            String sql = "delete from tb_produtos where id=?";
            
            //conectar o banco de dados e organizar o comando SQL
            PreparedStatement stmt = con.prepareStatement(sql);
            
            stmt.setInt(1, obj.getId());
            
            stmt.execute();
            stmt.close();
            
            JOptionPane.showMessageDialog(null,"Produto excluido com sucesso!");
            
        } catch (Exception erro) {
            
            JOptionPane.showMessageDialog(null, "Erro :" + erro);
        }
    }

    //metodo para listar os produtos
    public List<Produtos> listarProdutos() {
        try {

            // criar a lista
            List<Produtos> lista = new ArrayList<>();

            // criar o SQL, organizar a executar
            String sql = "select p.id, p.descricao, p.preco, p.qtd_estoque, f.nome from tb_produtos as p "
                    + "inner join tb_fornecedores as f on (p.for_id = f.id) "
                    + "order by p.id asc";;

            PreparedStatement stmt = con.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Produtos obj = new Produtos();
                Fornecedores f = new Fornecedores();

                obj.setId(rs.getInt("p.id"));
                obj.setDescricao(rs.getString("p.descricao"));
                obj.setPreco(rs.getDouble("p.preco"));
                obj.setQtd_estoque(rs.getInt("p.qtd_estoque"));

                f.setNome(rs.getString(("f.nome")));

                obj.setFornecedor(f);

                lista.add(obj);
            }

            return lista;

        } catch (SQLException erro) {
            JOptionPane.showMessageDialog(null, "Erro :" + erro);
            return null;
        }
    }
    
    //metodo para listar produtos por nome
    public List<Produtos> listarProdutosporNome(String nome) {
        try {

            // criar a lista
            List<Produtos> lista = new ArrayList<>();

            // criar o SQL, organizar a executar
            String sql = "select p.id, p.descricao, p.preco, p.qtd_estoque, f.nome from tb_produtos as p "
                    + "inner join tb_fornecedores as f on (p.for_id = f.id) where p.descricao like ? "
                    + "order by p.id asc";

            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.setString(1, nome);
            
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Produtos obj = new Produtos();
                Fornecedores f = new Fornecedores();

                obj.setId(rs.getInt("p.id"));
                obj.setDescricao(rs.getString("p.descricao"));
                obj.setPreco(rs.getDouble("p.preco"));
                obj.setQtd_estoque(rs.getInt("p.qtd_estoque"));

                f.setNome(rs.getString(("f.nome")));

                obj.setFornecedor(f);

                lista.add(obj);
            }

            return lista;

        } catch (SQLException erro) {
            JOptionPane.showMessageDialog(null, "Erro :" + erro);
            return null;
        }
    }
    
    //metodo para buscar produtos por nome
    public Produtos consultaPorNome(String nome) {
        try {

            // criar o SQL, organizar a executar
            String sql = "select p.id, p.descricao, p.preco, p.qtd_estoque, f.nome from tb_produtos as p "
                    + "inner join tb_fornecedores as f on (p.for_id = f.id) where p.descricao = ?";

            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.setString(1, nome);
            
            ResultSet rs = stmt.executeQuery();
            Produtos obj = new Produtos();
            Fornecedores f = new Fornecedores();
            
            if(rs.next()){

                obj.setId(rs.getInt("p.id"));
                obj.setDescricao(rs.getString("p.descricao"));
                obj.setPreco(rs.getDouble("p.preco"));
                obj.setQtd_estoque(rs.getInt("p.qtd_estoque"));

                f.setNome(rs.getString(("f.nome")));

                obj.setFornecedor(f);
            }

            return obj;

        } catch (SQLException erro) {
            JOptionPane.showMessageDialog(null, "Produto não encontrado");
            return null;
        }
    }
    
    //metodo para buscar produtos por código
    public Produtos buscaporcodigo(int id) {
        try {
            

            // criar o SQL, organizar a executar
            String sql = "select * from tb_produtos where id = ?";

            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.setInt(1, id);
            
            ResultSet rs = stmt.executeQuery();
            Produtos obj = new Produtos();
            
            if(rs.next()){

                obj.setId(rs.getInt("id"));
                obj.setDescricao(rs.getString("descricao"));
                obj.setPreco(rs.getDouble("preco"));
                obj.setQtd_estoque(rs.getInt("qtd_estoque"));

            }

            return obj;

        } catch (SQLException erro) {
            JOptionPane.showMessageDialog(null, "Produto não encontrado");
            return null;
        }
    }
    
    // metodo para dar baixa no estoque
    public void baixaEstoque(int id, int qtd_nova){
        try {
            
             String sql = "update tb_produtos set qtd_estoque=? where id=?";
             // conectar o banco de dados
             PreparedStatement stmt = con.prepareStatement(sql);
             
             stmt.setInt(1,qtd_nova);
             stmt.setInt(2, id);
             stmt.execute();
             stmt.close();
           
        } catch (Exception erro) {
            JOptionPane.showMessageDialog(null, "Erro : " + erro);
        }
    }
    
    //metodo para retornar o estoque atual do produto
    public int retornaEstoqueAtual(int id){
        try {
            int qtd_estoque = 0;
            
            String sql = "SELECT qtd_estoque from tb_produtos where id = ?";
            
            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.setInt(1, id);
            
            ResultSet rs = stmt.executeQuery();
            
            if(rs.next()){
                qtd_estoque = (rs.getInt("qtd_estoque"));
            }
            
            return qtd_estoque;             
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } 
    }
    
    //metodo de adicionar produto no estoque
    public void adicionarEstoque(int id, int qtd_nova){
        try {
            
             String sql = "update tb_produtos set qtd_estoque= ? where id=?";
             // conectar o banco de dados
             PreparedStatement stmt = con.prepareStatement(sql);
             
             stmt.setInt(1,qtd_nova);
             stmt.setInt(2, id);
             stmt.execute();
             stmt.close();
           
        } catch (Exception erro) {
            JOptionPane.showMessageDialog(null, "Erro : " + erro);
        }
    }
    
}
