/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package vista;

import java.awt.HeadlessException;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import logica.ControladoraLogica;
import logica.Ingredientes;
import logica.Receta;
import logica.RecetaDetalle;
import logica.UnidadMedida;

public class CrearReceta extends javax.swing.JFrame {

    private ControladoraLogica controladoraLogica = null;
    private List<Ingredientes> lIngredientes = null;
    private DefaultTableModel modeloTabla = null;
    private String nombreReceta = null;
    private String ingrediente = null;
    private Double cantidadIngrediente = 0.0;
    private Object[] lDatos = new Object[4];
    private List<Receta> listaReceta = null;
    private List<RecetaDetalle> listaRecetaDetalle = null;

    public CrearReceta() {

        controladoraLogica = new ControladoraLogica();
        lIngredientes = controladoraLogica.traerIngredientes();
        initComponents();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        txtNombreReceta = new javax.swing.JTextField();
        cbINgredientes = new javax.swing.JComboBox<>();
        txtCantidadIngrediente = new javax.swing.JTextField();
        btnAgregar = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tablaIngredientesReceta = new javax.swing.JTable();
        btnEliminar = new javax.swing.JButton();
        btnLimpiar = new javax.swing.JButton();
        btnGuardarReceta = new javax.swing.JButton();
        jLabel6 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Crear Receta");
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowOpened(java.awt.event.WindowEvent evt) {
                formWindowOpened(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("Liberation Sans", 0, 24)); // NOI18N
        jLabel1.setText("Crear Receta");

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder("Nombre Receta"));

        jLabel2.setText("Nombre Receta");

        jLabel3.setText("Ingrediente");

        jLabel4.setText("Cantidad");

        txtNombreReceta.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtNombreRecetaFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtNombreRecetaFocusLost(evt);
            }
        });

        cbINgredientes.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { " " }));

        txtCantidadIngrediente.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtCantidadIngredienteKeyPressed(evt);
            }
        });

        btnAgregar.setText("Agregar");
        btnAgregar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAgregarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel2)
                    .addComponent(jLabel3)
                    .addComponent(jLabel4))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(txtNombreReceta)
                        .addComponent(cbINgredientes, 0, 126, Short.MAX_VALUE))
                    .addComponent(txtCantidadIngrediente, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 126, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnAgregar)
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(txtNombreReceta, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel3)
                    .addComponent(cbINgredientes, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel4)
                    .addComponent(txtCantidadIngrediente, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnAgregar)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        tablaIngredientesReceta.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {},
                {},
                {},
                {}
            },
            new String [] {

            }
        ));
        jScrollPane1.setViewportView(tablaIngredientesReceta);

        btnEliminar.setText("Eliminar");
        btnEliminar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEliminarActionPerformed(evt);
            }
        });

        btnLimpiar.setText("Limpiar");
        btnLimpiar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLimpiarActionPerformed(evt);
            }
        });

        btnGuardarReceta.setText("Guardar");
        btnGuardarReceta.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGuardarRecetaActionPerformed(evt);
            }
        });

        jLabel6.setText("Lista de Ingredientes");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(77, 77, 77)
                        .addComponent(jLabel1))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(21, 21, 21)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(btnEliminar)
                                .addGap(12, 12, 12)
                                .addComponent(btnLimpiar)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(btnGuardarReceta))
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(84, 84, 84)
                        .addComponent(jLabel6)))
                .addContainerGap(17, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(17, 17, 17)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel6)
                .addGap(9, 9, 9)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 148, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnEliminar)
                    .addComponent(btnLimpiar)
                    .addComponent(btnGuardarReceta))
                .addContainerGap(29, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void formWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowOpened

        for (Ingredientes ingredientes : traerIngredientes()) {
            cbINgredientes.addItem(ingredientes.getNombre());
        }
        cbINgredientes.setSelectedIndex(1);
        crearTabla();
    }//GEN-LAST:event_formWindowOpened

    private void btnAgregarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAgregarActionPerformed
        agregarIngrediente();
    }//GEN-LAST:event_btnAgregarActionPerformed

    private void agregarIngrediente() throws NumberFormatException, HeadlessException {
        if (txtCantidadIngrediente.getText().equals("")
                || txtCantidadIngrediente.getText().isEmpty()
                || txtCantidadIngrediente.getText().isBlank()
                || cbINgredientes.getSelectedIndex() == 0
                || txtNombreReceta.getText().isBlank()
                || txtNombreReceta.getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Verifica informacion");
        } else {
            txtNombreReceta.setEditable(false);
            ingrediente = String.valueOf(cbINgredientes.getSelectedItem());
            for (Ingredientes i : lIngredientes) {
                if (i.getNombre().equals(ingrediente)) {
                    lDatos[0] = i.getNombre();
                    cantidadIngrediente = Double.parseDouble(txtCantidadIngrediente.getText());
                    lDatos[1] = cantidadIngrediente;
                    lDatos[2] = i.getIdUnidadMedida().getNombre();
                    lDatos[3] = i.getPrecio() * cantidadIngrediente;
                    break;
                }
            }
            modeloTabla.addRow(lDatos);
        }
    }

    private void txtCantidadIngredienteKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCantidadIngredienteKeyPressed

        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            agregarIngrediente();
        }
    }//GEN-LAST:event_txtCantidadIngredienteKeyPressed

    private void btnEliminarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEliminarActionPerformed
        if (tablaIngredientesReceta.getRowCount() > 0) {
            if (tablaIngredientesReceta.getSelectedRow() != -1) {
                modeloTabla.removeRow(tablaIngredientesReceta.getSelectedRow());
            } else {
                JOptionPane.showMessageDialog(null, "Seleccione algo a Eliminar");
            }
        } else {
            JOptionPane.showMessageDialog(null, "No hay nada en la tabla");
        }
    }//GEN-LAST:event_btnEliminarActionPerformed

    private void btnLimpiarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLimpiarActionPerformed
        if (tablaIngredientesReceta.getRowCount() > 0) {
            if (JOptionPane.showConfirmDialog(null, "¿Desea Eliminar todos los Datos?", "Confirmar", JOptionPane.YES_NO_CANCEL_OPTION) == 1) {
                modeloTabla.setRowCount(0);
            }
        } else {
            JOptionPane.showMessageDialog(null, "No hay nada que Limpiar");
        }
    }//GEN-LAST:event_btnLimpiarActionPerformed

    private void txtNombreRecetaFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtNombreRecetaFocusLost
        listaReceta = controladoraLogica.traerRecetas();
        for (Receta receta : listaReceta) {
            if (receta.getNombreReceta().equalsIgnoreCase(txtNombreReceta.getText())) {
                JOptionPane.showMessageDialog(null, "Ya existe ese nombre de Receta", "Duplicidad en Receta", JOptionPane.ERROR_MESSAGE);
                cbINgredientes.setEnabled(false);
                txtCantidadIngrediente.setEditable(false);
                btnAgregar.setEnabled(false);
            }
        }
    }//GEN-LAST:event_txtNombreRecetaFocusLost

    private void txtNombreRecetaFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtNombreRecetaFocusGained
        cbINgredientes.setEnabled(true);
        txtCantidadIngrediente.setEditable(true);
        btnAgregar.setEnabled(true);
    }//GEN-LAST:event_txtNombreRecetaFocusGained

    private void btnGuardarRecetaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGuardarRecetaActionPerformed

        if (tablaIngredientesReceta.getRowCount() > 0) {

            controladoraLogica.crearReceta(txtNombreReceta.getText());
            Receta receta = controladoraLogica.traerReceta(txtNombreReceta.getText());

            for (int i = 0; i < tablaIngredientesReceta.getRowCount(); i++) {

                Ingredientes ingrediente = controladoraLogica.traerIngrediente(tablaIngredientesReceta.getValueAt(i, 0).toString());
                controladoraLogica.crearDetalleReceta(
                        (double) tablaIngredientesReceta.getValueAt(i, 1),
                        (double) tablaIngredientesReceta.getValueAt(i, 3),
                        ingrediente,
                        receta);

            }
            JOptionPane.showMessageDialog(null, "Se Creo la Receta con Exito");
        } else {
            JOptionPane.showMessageDialog(null, "No hay nada que guardar");
        }

    }//GEN-LAST:event_btnGuardarRecetaActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAgregar;
    private javax.swing.JButton btnEliminar;
    private javax.swing.JButton btnGuardarReceta;
    private javax.swing.JButton btnLimpiar;
    private javax.swing.JComboBox<String> cbINgredientes;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable tablaIngredientesReceta;
    private javax.swing.JTextField txtCantidadIngrediente;
    private javax.swing.JTextField txtNombreReceta;
    // End of variables declaration//GEN-END:variables

    private List<Ingredientes> traerIngredientes() {
        return controladoraLogica.traerIngredientes();
    }

    private void crearTabla() {
        modeloTabla = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        String[] informacion = {"Ingrediente", "Cantidad", "Medida", "Costo"};
        modeloTabla.setColumnIdentifiers(informacion);
        tablaIngredientesReceta.setModel(modeloTabla);
    }

}
