/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.projeto.dao;

import br.com.projeto.jbdc.ConnectionFactory;
import br.com.projeto.model.Funcionarios;
import br.com.projeto.view.FormFuncionarios;
import br.com.projeto.view.FormLogin;
import br.com.projeto.view.FormMenu;
import java.awt.Font;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.UIManager;

/**
 *
 * @author joaov
 */
public class FuncionariosDAO {

    //conexao BD
    private Connection con;

    public FuncionariosDAO() {
        this.con = new ConnectionFactory().getConnection();
    }

    // metodo para cadastrar funcionario
    public void cadastrarFuncionarios(Funcionarios obj) {

        try {

            if (cpfExiste(obj.getCpf())) {
            JOptionPane.showMessageDialog(null, "CPF já cadastrado!");
            return; 
        }
            
            // 1° criar o comando SQL
            String sql = "insert into tb_funcionarios (nome,rg,cpf,email,senha,cargo,nivel_acesso,telefone,celular,cep,endereco,numero,complemento,bairro,cidade,estado)"
                    + "values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

            // 2° conectar o banco de dados e organizar o comando sql 
            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.setString(1, obj.getNome());
            stmt.setString(2, obj.getRg());
            stmt.setString(3, obj.getCpf());
            stmt.setString(4, obj.getEmail());
            stmt.setString(5, obj.getSenha());
            stmt.setString(6, obj.getCargo());
            stmt.setString(7, obj.getNivel_acesso());
            stmt.setString(8, obj.getTelefone());
            stmt.setString(9, obj.getCelular());
            stmt.setString(10, obj.getCep());
            stmt.setString(11, obj.getEndereco());
            stmt.setInt(12, obj.getNumero());
            stmt.setString(13, obj.getComplemento());
            stmt.setString(14, obj.getBairro());
            stmt.setString(15, obj.getCidade());
            stmt.setString(16, obj.getUf());

            // 3° Executar o comando sql
            stmt.execute();
            stmt.close();

            JOptionPane.showMessageDialog(null, "Cadastrado com Sucesso!");

        } catch (SQLException erro) {
            JOptionPane.showMessageDialog(null, "Erro:" + erro);
        }
    }
    
    public boolean cpfExiste(String cpf) {
        try {

            String sql = "SELECT * FROM tb_funcionarios WHERE cpf = ?";

            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.setString(1, cpf);

            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return true;
            }

            stmt.close();
        } catch (SQLException erro) {
            JOptionPane.showMessageDialog(null, "Erro ao verificar CPF: " + erro);
        }

        return false;
    }

    //metodo para alterar Funcionario
    public void alterarFuncionarios(Funcionarios obj) {

        try {
            
            if (cpfExiste(obj.getCpf())) {
            JOptionPane.showMessageDialog(null, "CPF já cadastrado!");
            return; // Não prosseguir com o cadastro
        }

            // 1° criar o comando alterar
            String sql = "update tb_funcionarios set nome=?, rg=?, cpf=?, email=?,senha=?, cargo=?, nivel_acesso=?,telefone=?, celular=?,cep=? ,endereco=?,"
                    + "numero=? ,complemento=? ,bairro=?,cidade=?,estado=? where id=?";

            // 2° conectar o banco de dados e organizar o comando sql 
            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.setString(1, obj.getNome());
            stmt.setString(2, obj.getRg());
            stmt.setString(3, obj.getCpf());
            stmt.setString(4, obj.getEmail());
            stmt.setString(5, obj.getSenha());
            stmt.setString(6, obj.getCargo());
            stmt.setString(7, obj.getNivel_acesso());
            stmt.setString(8, obj.getTelefone());
            stmt.setString(9, obj.getCelular());
            stmt.setString(10, obj.getCep());
            stmt.setString(11, obj.getEndereco());
            stmt.setInt(12, obj.getNumero());
            stmt.setString(13, obj.getComplemento());
            stmt.setString(14, obj.getBairro());
            stmt.setString(15, obj.getCidade());
            stmt.setString(16, obj.getUf());

            stmt.setInt(17, obj.getId());

            // 3° Executar o comando sql
            stmt.execute();
            stmt.close();

            JOptionPane.showMessageDialog(null, "Alterado com Sucesso!");

        } catch (SQLException erro) {
            JOptionPane.showMessageDialog(null, "Erro:" + erro);
        }

    }

    //metodo para excluir Funcionario
    public void excluirFuncionario(Funcionarios obj) {
        try {

            // 1° comando para deletar funcionario, com o id
            String sql = "delete from tb_funcionarios where id = ?";

            // 2° conectar o banco de dados e organizar o comando sql 
            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.setInt(1, obj.getId());

            // 3° Executar o comando sql
            stmt.execute();
            stmt.close();

            JOptionPane.showMessageDialog(null, "Excluido com Sucesso!");

        } catch (SQLException erro) {
            JOptionPane.showMessageDialog(null, "Erro:" + erro);
        }

    }

    // metodo para listar funcionários
    public List<Funcionarios> listarFuncionarios() {

        try {

            // 1° criar a lista 
            List<Funcionarios> lista = new ArrayList<>();

            // 2° criar o sql, organizar e executar
            String sql = "select * from tb_funcionarios";
            PreparedStatement stmt = con.prepareStatement(sql);

            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Funcionarios obj = new Funcionarios();

                obj.setId(rs.getInt("id"));
                obj.setNome(rs.getString("nome"));
                obj.setRg(rs.getString("rg"));
                obj.setCpf(rs.getString("cpf"));
                obj.setEmail(rs.getString("email"));
                obj.setSenha(rs.getString("senha"));
                obj.setCargo(rs.getString("cargo"));
                obj.setNivel_acesso(rs.getString("nivel_acesso"));
                obj.setTelefone(rs.getString("telefone"));
                obj.setCelular(rs.getString("celular"));
                obj.setCep(rs.getString("cep"));
                obj.setEndereco(rs.getString("endereco"));
                obj.setNumero(rs.getInt("numero"));
                obj.setComplemento(rs.getString("complemento"));
                obj.setBairro(rs.getString("bairro"));
                obj.setCidade(rs.getString("cidade"));
                obj.setUf(rs.getString("estado"));

                lista.add(obj);
            }

            return lista;

        } catch (Exception erro) {
            JOptionPane.showMessageDialog(null, "Erro: " + erro);
            return null;
        }
    }

    public Funcionarios consultaPorNome(String nome) {
        //consulta funcionario por nome
        try {

            String sql = "select * from tb_funcionarios where nome = ?";
            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.setString(1, nome);

            ResultSet rs = stmt.executeQuery();
            Funcionarios obj = new Funcionarios();

            if (rs.next()) {
                obj.setId(rs.getInt("id"));
                obj.setNome(rs.getString("nome"));
                obj.setRg(rs.getString("rg"));
                obj.setCpf(rs.getString("cpf"));
                obj.setEmail(rs.getString("email"));
                obj.setSenha(rs.getString("senha"));
                obj.setCargo(rs.getString("cargo"));
                obj.setNivel_acesso(rs.getString("nivel_acesso"));
                obj.setTelefone(rs.getString("telefone"));
                obj.setCelular(rs.getString("celular"));
                obj.setCep(rs.getString("cep"));
                obj.setEndereco(rs.getString("endereco"));
                obj.setNumero(rs.getInt("numero"));
                obj.setComplemento(rs.getString("complemento"));
                obj.setBairro(rs.getString("bairro"));
                obj.setCidade(rs.getString("cidade"));
                obj.setUf(rs.getString("estado"));
            }
            return obj;
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Funcionário não localizado!");
            return null;
        }
    }

    public List<Funcionarios> listaFuncionarioporNome(String nome) {
        //lista o funcionario pelo nome
        try {

            // 1° criar a lista 
            List<Funcionarios> lista = new ArrayList<>();

            // 2° criar o sql, organizar e executar
            String sql = "select * from tb_funcionarios where nome like ?";
            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.setString(1, nome);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Funcionarios obj = new Funcionarios();
                obj.setId(rs.getInt("id"));
                obj.setNome(rs.getString("nome"));
                obj.setRg(rs.getString("rg"));
                obj.setCpf(rs.getString("cpf"));
                obj.setEmail(rs.getString("email"));
                obj.setSenha(rs.getString("senha"));
                obj.setCargo(rs.getString("cargo"));
                obj.setNivel_acesso(rs.getString("nivel_acesso"));
                obj.setTelefone(rs.getString("telefone"));
                obj.setCelular(rs.getString("celular"));
                obj.setCep(rs.getString("cep"));
                obj.setEndereco(rs.getString("endereco"));
                obj.setNumero(rs.getInt("numero"));
                obj.setComplemento(rs.getString("complemento"));
                obj.setBairro(rs.getString("bairro"));
                obj.setCidade(rs.getString("cidade"));
                obj.setUf(rs.getString("estado"));

                lista.add(obj);
            }

            return lista;

        } catch (Exception erro) {
            JOptionPane.showMessageDialog(null, "Erro: " + erro);
            return null;
        }
    }

    // metodo de efetuar login
    public void efetuarlogin(String email, String senha) {
        try {
            // 1° comando SQL
            String sql = "select * from tb_funcionarios where email=? and senha=?";
            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.setString(1, email);
            stmt.setString(2, senha);

            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                // usuario entrou no sistema

                //se o usuario for Admin
                if (rs.getString("nivel_acesso").equals("Administrador")) {
                    UIManager.put("OptionPane.messageFont", new Font("Tahoma", Font.PLAIN, 18));
                    JOptionPane.showMessageDialog(null, "Seja bem vindo ao Sistema");
                    FormMenu tela = new FormMenu();
                    tela.usuariologado = rs.getString("nome");
                    tela.nivel = rs.getString("nivel_acesso");
                    tela.setVisible(true);
                } //se o usuario for funcionário
                else if (rs.getString("nivel_acesso").equals("Usuário")) {
                    UIManager.put("OptionPane.messageFont", new Font("Tahoma", Font.PLAIN, 18));
                    JOptionPane.showMessageDialog(null, "Seja bem vindo ao Sistema");
                    FormMenu tela = new FormMenu();
                    tela.usuariologado = rs.getString("nome");
                    tela.nivel = rs.getString("nivel_acesso");

                    //comando para desabilitar o menu (niveis de acesso)
                    tela.menu_posicao.setVisible(false);
                    tela.menu_historicovendas.setVisible(false);
                    tela.setVisible(true);
                }
                    
            }   else {
                //dados errados
                    UIManager.put("OptionPane.messageFont", new Font("Tahoma", Font.PLAIN, 18));
                    JOptionPane.showMessageDialog(null, "Dados incorretos!");
                    new FormLogin().setVisible(true);
            }

        } catch (Exception erro) {
            JOptionPane.showMessageDialog(null, "Erro:" + erro);
        }
    }
}
