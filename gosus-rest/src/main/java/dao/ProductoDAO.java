package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;

import util.ConexionBD;
import dominio.Producto;
import excepcion.DAOExcepcion;

public class ProductoDAO extends BaseDAO {

	public Producto insertar(Producto oProducto) throws DAOExcepcion {
		String query = "insert into producto(producto,precio,idTipo) values (?,?,?)";
		Connection con = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			con = ConexionBD.obtenerConexion();
			stmt = con.prepareStatement(query);
			stmt.setString(1, oProducto.getProducto());
			stmt.setDouble(2, oProducto.getPrecio());
			stmt.setInt(3, 3);
					
			int i = stmt.executeUpdate();
			if (i != 1) {
				throw new SQLException("No se pudo insertar");
			}
			// Obtener el ultimo id
			int id = 0;
			query = "select last_insert_id()";
			stmt = con.prepareStatement(query);
			rs = stmt.executeQuery();
			if (rs.next()) {
				id = rs.getInt(1);
			}
			oProducto.setIdProducto(id);

		} catch (SQLException e) {
			System.err.println(e.getMessage());
			throw new DAOExcepcion(e.getMessage());
		} finally {
			this.cerrarResultSet(rs);
			this.cerrarStatement(stmt);
			this.cerrarConexion(con);
		}
		return oProducto;
	}

	public Producto obtener(int idProducto) throws DAOExcepcion {
		Producto oProducto = new Producto();
		Connection con = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			String query = "select idProducto, producto, precio, idTipo from producto where idproducto=?";
			con = ConexionBD.obtenerConexion();
			stmt = con.prepareStatement(query);
			stmt.setInt(1, idProducto);
			rs = stmt.executeQuery();
			if (rs.next()) {

				oProducto.setIdProducto(rs.getInt(1));
				oProducto.setProducto(rs.getString(2));
				oProducto.setPrecio(rs.getDouble(3));
			
			}
		} catch (SQLException e) {
			System.err.println(e.getMessage());
			throw new DAOExcepcion(e.getMessage());
		} finally {
			this.cerrarResultSet(rs);
			this.cerrarStatement(stmt);
			this.cerrarConexion(con);
		}
		return oProducto;
	}

	public void eliminar(int idProducto) throws DAOExcepcion {
		
		String query = "delete from producto WHERE idproducto=?";
		Connection con = null;
		PreparedStatement stmt = null;
		try {
			con = ConexionBD.obtenerConexion();
			stmt = con.prepareStatement(query);
			stmt.setInt(1, idProducto);
			int i = stmt.executeUpdate();
			if (i != 1) {
				throw new SQLException("No se pudo eliminar");
			}
		} catch (SQLException e) {
			System.err.println(e.getMessage());
			throw new DAOExcepcion(e.getMessage());
		} finally {
			this.cerrarStatement(stmt);
			this.cerrarConexion(con);
		}
	}
	
	public Producto actualizar(Producto oProducto) throws DAOExcepcion {
		String query = "update producto set producto=?,precio=?,idTipo=? where idProducto=?";
		Connection con = null;
		PreparedStatement stmt = null;
		try {
			con = ConexionBD.obtenerConexion();
			stmt = con.prepareStatement(query);
			stmt.setString(1, oProducto.getProducto());
			stmt.setDouble(2, oProducto.getPrecio());
			stmt.setInt(3, 3);
			stmt.setInt(4, oProducto.getIdProducto());
			int i = stmt.executeUpdate();
			if (i != 1) {
				throw new SQLException("No se pudo actualizar");
			}
		} catch (SQLException e) {
			System.err.println(e.getMessage());
			throw new DAOExcepcion(e.getMessage());
		} finally {
			this.cerrarStatement(stmt);
			this.cerrarConexion(con);
		}
		return oProducto;
	}

	public Collection<Producto> buscarArticuloxTipo(String nombre, int idTipo)
				throws DAOExcepcion {
	
		String query = "select idProducto, producto, precio, idTipo from producto where idTipo = ? and producto like ?";
		Collection<Producto> lista = new ArrayList<Producto>();
		Connection con = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			con = ConexionBD.obtenerConexion();
			stmt = con.prepareStatement(query);
			stmt.setInt(1, idTipo);
			stmt.setString(2, "%" + nombre + "%");
			rs = stmt.executeQuery();
			 while (rs.next()) {	 
				Producto vo = new Producto();			
				vo.setIdProducto(rs.getInt("idProducto"));
				vo.setProducto(rs.getString("producto"));
				vo.setPrecio(rs.getDouble("precio"));
				lista.add(vo);
			}
		} catch (SQLException e) {
			System.err.println(e.getMessage());
			throw new DAOExcepcion(e.getMessage());
		} finally {
			this.cerrarResultSet(rs);
			this.cerrarStatement(stmt);
			this.cerrarConexion(con);
		}
		return lista;
	}
	
	public Collection<Producto> buscarArticuloxTipoxRango(String nombre, int filaInicial, int cantFilas, int idTipo)
	throws DAOExcepcion {
		
		String query = "select idProducto, producto, precio, idTipo from producto where idTipo = ? and producto like ? limit ?, ? ";
		Collection<Producto> lista = new ArrayList<Producto>();
		Connection con = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			con = ConexionBD.obtenerConexion();
			stmt = con.prepareStatement(query);
			stmt.setInt(1, idTipo);
			stmt.setString(2, "%" + nombre + "%");
			stmt.setInt(3, filaInicial);
			stmt.setInt(4, cantFilas);
			rs = stmt.executeQuery();
			 while (rs.next()) {	 
				Producto vo = new Producto();			
				vo.setIdProducto(rs.getInt("idProducto"));
				vo.setProducto(rs.getString("producto"));
				vo.setPrecio(rs.getDouble("precio"));
				lista.add(vo);
			}
		} catch (SQLException e) {
		System.err.println(e.getMessage());
		throw new DAOExcepcion(e.getMessage());
		} finally {
		this.cerrarResultSet(rs);
		this.cerrarStatement(stmt);
		this.cerrarConexion(con);
		}
		return lista;
	}
}
