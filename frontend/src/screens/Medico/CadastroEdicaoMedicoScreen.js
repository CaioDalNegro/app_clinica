import React from 'react';
import MedicoForm from '../../components/MedicoForm';
import api from '../../services/api';

export default function CadastroEdicaoMedicoScreen({ route, navigation }) {

  const medico = route.params?.medico || null;

  const salvar = async (payload) => {
    try {
      if (medico) {
        await api.put(`/medicos/${medico.id}`, payload);
      } else {
        await api.post("/medicos", payload);
      }

      Alert.alert("Sucesso", "Operação concluída!");
      navigation.goBack();

    } catch (error) {
      console.log("Erro ao salvar médico:", error);
      Alert.alert("Erro", "Não foi possível salvar.");
    }
  };

  return (
    <MedicoForm 
      medico={medico} 
      onSave={salvar} 
      navigation={navigation}
    />
  );
}