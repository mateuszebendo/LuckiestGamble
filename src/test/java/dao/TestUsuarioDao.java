package dao;

import org.cefet.dao.UsuarioDAO;
import org.cefet.enums.TipoUsuario;
import org.cefet.models.UsuarioModel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.sql.*;
import java.util.Date;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class TestUsuarioDao {
    @Mock
    private Connection mockConnection;
    @Mock
    private PreparedStatement mockPreparedStatement;
    @Mock
    private ResultSet mockResultSet;
    // Usado para obter o ID gerado automaticamente pelo banco de dados
    @Mock
    private ResultSet mockGeneratedKeysResultSet;

    private UsuarioDAO usuarioDAO;

    @BeforeEach
    void setUp() throws SQLException {
        MockitoAnnotations.openMocks(this); // Inicializa os mocks
        usuarioDAO = new UsuarioDAO(mockConnection); // Injeta o mock da conexão

        // Comportamento padrão para prepareStatement
        // Sempre que o mockConnection chamar prepareStatement() com qualquer string como argumento, retorne o mockPreparedStatement
        when(mockConnection.prepareStatement(anyString())).thenReturn(mockPreparedStatement);
        when(mockConnection.prepareStatement(anyString(), eq(Statement.RETURN_GENERATED_KEYS))).thenReturn(mockPreparedStatement);
        // Comportamento padrão para executeQuery
        when(mockPreparedStatement.executeQuery()).thenReturn(mockResultSet);
        // Comportamento padrão para generatedKeys (usado para recuperar o ID gerado pelo Banco de Dados)
        when(mockPreparedStatement.getGeneratedKeys()).thenReturn(mockGeneratedKeysResultSet);
        when(mockGeneratedKeysResultSet.next()).thenReturn(true);
        when(mockGeneratedKeysResultSet.getLong(anyInt())).thenReturn(1L);
    }

    @Test
    void testSave_UsuarioValido() throws SQLException {
        //Arrange
        String nomeUsuario = "testeNome";
        Date testDate = new Date(1678886400000L);
        UsuarioModel expectedUser = new UsuarioModel();
        expectedUser.setNome(nomeUsuario);
        expectedUser.setEmail("teste@email.com");
        expectedUser.setDataNascimento(testDate);
        expectedUser.setSenha("senhaHash");
        expectedUser.setTipoUsuario(TipoUsuario.COMUM);
        expectedUser.setSaldo(100.0);

        when(mockResultSet.next()).thenReturn(true).thenReturn(false);
        when(mockResultSet.getLong("usuario_id")).thenReturn(1L);
        when(mockResultSet.getString("nome")).thenReturn(expectedUser.getNome());
        when(mockResultSet.getString("email")).thenReturn(expectedUser.getEmail());
        when(mockResultSet.getString("senha")).thenReturn("senhaHash");
        when(mockResultSet.getDate("data_nascimento")).thenReturn(new java.sql.Date(testDate.getTime()));
        when(mockResultSet.getDouble("saldo")).thenReturn(expectedUser.getSaldo());
        when(mockResultSet.getString("tipo_usuario")).thenReturn(expectedUser.getTipoUsuario().name());

        //Act
        UsuarioModel newUser = usuarioDAO.save(expectedUser);

        //Assert
        assertEquals(expectedUser.getUsuarioId(), newUser.getUsuarioId());
        assertEquals(expectedUser.getNome(), newUser.getNome());
        assertEquals(expectedUser.getEmail(), newUser.getEmail());
        assertEquals(expectedUser.getSenha(), newUser.getSenha());
        assertEquals(expectedUser.getDataNascimento().getTime(), newUser.getDataNascimento().getTime());
        assertEquals(expectedUser.getSaldo(), newUser.getSaldo());
        assertEquals(expectedUser.getTipoUsuario(), newUser.getTipoUsuario());

        // Verificar interações com mocks
        verify(mockConnection).prepareStatement(
                argThat(sql -> sql.startsWith("INSERT INTO usuarios")), // Verifica que a SQL começa com INSERT
                eq(Statement.RETURN_GENERATED_KEYS) // Verifica a flag
        );

        // 2. Verificar se os parâmetros foram setados corretamente no PreparedStatement
        verify(mockPreparedStatement).setString(anyInt(), eq(newUser.getNome())); //eq() é um argument matcher (comparador de argumentos) do Mockito
        verify(mockPreparedStatement).setString(anyInt(), eq(newUser.getEmail()));
        verify(mockPreparedStatement).setString(anyInt(), eq(newUser.getSenha()));
        verify(mockPreparedStatement).setDate(anyInt(), eq(new java.sql.Date(newUser.getDataNascimento().getTime())));
        verify(mockPreparedStatement).setDouble(anyInt(), eq(newUser.getSaldo()));
        verify(mockPreparedStatement).setString(anyInt(), eq(newUser.getTipoUsuario().name()));

        // 3. Verificar se executeUpdate() foi chamado
        verify(mockPreparedStatement).executeUpdate();

        // 4. Verificar se getGeneratedKeys() foi chamado
        verify(mockPreparedStatement).getGeneratedKeys();

        // 5. Verificar se o ResultSet de chaves geradas foi manipulado
        verify(mockGeneratedKeysResultSet).next();
        verify(mockGeneratedKeysResultSet).getLong(anyInt());
    }

    @Test
    void testFindByNome_UsuarioEncontrado() throws SQLException {
        // Arrange
        String nomeUsuario = "testeNome";
        Date testDate = new Date(1678886400000L);
        UsuarioModel expectedUser = new UsuarioModel();
        expectedUser.setUsuarioId(1L);
        expectedUser.setNome(nomeUsuario);
        expectedUser.setEmail("teste@email.com");
        expectedUser.setDataNascimento(testDate);
        expectedUser.setSenha("senhaHash");
        expectedUser.setTipoUsuario(TipoUsuario.COMUM);
        expectedUser.setSaldo(100.0);

        when(mockResultSet.next()).thenReturn(true).thenReturn(false);
        when(mockResultSet.getLong("usuario_id")).thenReturn(expectedUser.getUsuarioId());
        when(mockResultSet.getString("nome")).thenReturn(expectedUser.getNome());
        when(mockResultSet.getString("email")).thenReturn(expectedUser.getEmail());
        when(mockResultSet.getString("senha")).thenReturn("senhaHash");
        when(mockResultSet.getDate("data_nascimento")).thenReturn(new java.sql.Date(testDate.getTime()));
        when(mockResultSet.getDouble("saldo")).thenReturn(expectedUser.getSaldo());
        when(mockResultSet.getString("tipo_usuario")).thenReturn(expectedUser.getTipoUsuario().name());

        // Act
        Optional<UsuarioModel> result = usuarioDAO.findByNome(nomeUsuario);

        // Assert
        assertTrue(result.isPresent(), "Opcional deve conter um usuário.");
        UsuarioModel actualUser = result.get();
        assertEquals(expectedUser.getUsuarioId(), actualUser.getUsuarioId());
        assertEquals(expectedUser.getNome(), actualUser.getNome());
        assertEquals(expectedUser.getEmail(), actualUser.getEmail());
        assertEquals(expectedUser.getSenha(), actualUser.getSenha());
        assertEquals(expectedUser.getDataNascimento().getTime(), actualUser.getDataNascimento().getTime());
        assertEquals(expectedUser.getSaldo(), actualUser.getSaldo());
        assertEquals(expectedUser.getTipoUsuario(), actualUser.getTipoUsuario());

        // Verificar interações com mocks
        verify(mockConnection).prepareStatement("SELECT usuario_id, nome, email, senha, data_nascimento, saldo, tipo_usuario, data_criacao, data_atualizacao FROM usuarios WHERE nome = ?");
        verify(mockPreparedStatement).setString(1, nomeUsuario);
        verify(mockPreparedStatement).executeQuery();
        verify(mockResultSet, times(1)).next();
        verify(mockResultSet, atLeastOnce()).getLong(anyString());
    }

    @Test
    void testUpdate_UsuarioValido() throws SQLException {
        //Arrange
        String nomeUsuario = "testeNome";
        Date testDate = new Date(1678886400000L);
        UsuarioModel expectedUser = new UsuarioModel();
        expectedUser.setUsuarioId(1L);
        expectedUser.setNome(nomeUsuario);
        expectedUser.setEmail("teste@email.com");
        expectedUser.setDataNascimento(testDate);
        expectedUser.setSenha("senhaHash");
        expectedUser.setTipoUsuario(TipoUsuario.COMUM);
        expectedUser.setSaldo(100.0);

        when(mockResultSet.next()).thenReturn(true).thenReturn(false);
        when(mockResultSet.getLong("usuario_id")).thenReturn(expectedUser.getUsuarioId());
        when(mockResultSet.getString("nome")).thenReturn(expectedUser.getNome());
        when(mockResultSet.getString("email")).thenReturn(expectedUser.getEmail());
        when(mockResultSet.getString("senha")).thenReturn("senhaHash");
        when(mockResultSet.getDate("data_nascimento")).thenReturn(new java.sql.Date(testDate.getTime()));
        when(mockResultSet.getDouble("saldo")).thenReturn(expectedUser.getSaldo());
        when(mockResultSet.getString("tipo_usuario")).thenReturn(expectedUser.getTipoUsuario().name());

        //Act
        UsuarioModel updateUser = usuarioDAO.save(expectedUser);

        //Assert
        assertEquals(expectedUser.getUsuarioId(), updateUser.getUsuarioId());
        assertEquals(expectedUser.getNome(), updateUser.getNome());
        assertEquals(expectedUser.getEmail(), updateUser.getEmail());
        assertEquals(expectedUser.getSenha(), updateUser.getSenha());
        assertEquals(expectedUser.getDataNascimento().getTime(), updateUser.getDataNascimento().getTime());
        assertEquals(expectedUser.getSaldo(), updateUser.getSaldo());
        assertEquals(expectedUser.getTipoUsuario(), updateUser.getTipoUsuario());

        // Verificar interações com mocks
        verify(mockConnection).prepareStatement(
                argThat(sql -> sql.startsWith("UPDATE usuarios SET"))
        );

        // 2. Verificar se os parâmetros foram setados corretamente no PreparedStatement
        verify(mockPreparedStatement).setString(anyInt(), eq(updateUser.getNome()));
        verify(mockPreparedStatement).setString(anyInt(), eq(updateUser.getEmail()));
        verify(mockPreparedStatement).setString(anyInt(), eq(updateUser.getSenha()));
        verify(mockPreparedStatement).setDate(anyInt(), eq(new java.sql.Date(updateUser.getDataNascimento().getTime())));
        verify(mockPreparedStatement).setDouble(anyInt(), eq(updateUser.getSaldo()));
        verify(mockPreparedStatement).setString(anyInt(), eq(updateUser.getTipoUsuario().name()));

        // 3. Verificar se executeUpdate() foi chamado
        verify(mockPreparedStatement).executeUpdate();
    }

    @Test
    void testDelete_IdDeUsarioValido() throws SQLException {
        //Arrange
        Long userId = 1L;

        //Act
        usuarioDAO.deleteById(userId);

        //Assert
        verify(mockConnection).prepareStatement(
                argThat(sql -> sql.startsWith("DELETE FROM usuarios WHERE usuario_id = ?"))
        );
        verify(mockPreparedStatement).executeUpdate();
    }

}
