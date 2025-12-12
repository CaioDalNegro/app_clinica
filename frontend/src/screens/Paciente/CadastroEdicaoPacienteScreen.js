// Tela responsável por CADASTRAR ou EDITAR um paciente
import React from "react";
import { Alert } from "react-native";
import PacienteForm from "../../components/PacienteForm";
import api from "../../services/api";

export default function CadastroEdicaoPacienteScreen({ route, navigation }) {

  // Se vier um paciente via navegação, estamos EDITANDO.
  // Se não vier nada, estamos CADASTRANDO.
  const paciente = route.params?.paciente || null;

  // Função chamada quando o botão Salvar é pressionado no formulário
  const salvar = async (payload) => {
    try {
      if (paciente) {
        // EDITAR → manda PUT com o ID
        await api.put(`/pacientes/${paciente.id}`, payload);
      } else {
        // CADASTRAR → manda POST
        await api.post("/pacientes", payload);
      }

      Alert.alert("Sucesso", "Operação concluída!");
      navigation.goBack(); // Volta para a tela anterior

    } catch (error) {
      console.log("Erro ao salvar paciente:", error);
      Alert.alert("Erro", "Não foi possível salvar o paciente.");
    }
  };

  // Renderiza o formulário
  return (
    <PacienteForm
      paciente={paciente}
      onSave={salvar}
      navigation={navigation}
    />
  );
}