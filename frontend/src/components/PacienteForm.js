// Formulário utilizado para CADASTRO e EDIÇÃO de paciente

import React, { useState, useEffect } from "react";
import {
  View, Text, TextInput, ScrollView,
  StyleSheet, TouchableOpacity, Alert
} from "react-native";

// Estado inicial vazio
const initialState = {
  nome: "",
  cpf: "",
  email: "",
  telefone: "",
  logradouro: "",
  numero: "",
  complemento: "",
  bairro: "",
  cidade: "",
  uf: "",
  cep: "",
};

export default function PacienteForm({ paciente, onSave, navigation }) {

  // Preenche com dados caso esteja editando
  const [formData, setFormData] = useState(paciente || initialState);

  // Armazena erros de validação
  const [errors, setErrors] = useState({});

  const isEditing = !!paciente;
  const buttonTitle = isEditing ? "Salvar Alterações" : "Cadastrar Paciente";

  useEffect(() => {
    setFormData(paciente || initialState);
  }, [paciente]);

  // Atualiza o campo digitado
  const handleChange = (name, value) => {
    setFormData((prev) => ({ ...prev, [name]: value }));
  };

  // Validação simples no front-end
  const validate = () => {

    // Campos obrigatórios
    const required = ["nome", "telefone", "logradouro", "bairro", "cidade", "uf"];

    if (!isEditing) {
      required.push("cpf", "email"); // No cadastro CPF e email são obrigatórios
    }

    let ok = true;
    const e = {};

    required.forEach((field) => {
      if (!formData[field]) {
        ok = false;
        e[field] = "Campo obrigatório";
      }
    });

    setErrors(e);
    return ok;
  };

  // Envia o formulário
  const submit = () => {
    if (!validate()) {
      Alert.alert("Erro", "Preencha todos os campos obrigatórios.");
      return;
    }

    // Objeto no formato esperado pelo backend
    const payload = {
      nome: formData.nome,
      cpf: formData.cpf,
      email: formData.email,
      telefone: formData.telefone,
      endereco: {
        logradouro: formData.logradouro,
        numero: formData.numero,
        complemento: formData.complemento,
        bairro: formData.bairro,
        cidade: formData.cidade,
        uf: formData.uf,
        cep: formData.cep,
      },
    };

    // No caso de edição, o id deve ir junto
    if (isEditing) payload.id = paciente.id;

    // Chama função da tela anterior
    onSave(payload);
  };

  return (
    <View style={styles.container}>
      <ScrollView style={styles.scroll}>

        <Text style={styles.title}>
          {isEditing ? "Editar Paciente" : "Cadastrar Paciente"}
        </Text>

        {/* Campos */}
        <TextInput
          style={styles.input}
          placeholder="Nome"
          value={formData.nome}
          onChangeText={(t) => handleChange("nome", t)}
        />
        {errors.nome && <Text style={styles.error}>{errors.nome}</Text>}

        {!isEditing && (
          <>
            <TextInput
              style={styles.input}
              placeholder="CPF"
              value={formData.cpf}
              onChangeText={(t) => handleChange("cpf", t)}
            />
            <TextInput
              style={styles.input}
              placeholder="Email"
              value={formData.email}
              onChangeText={(t) => handleChange("email", t)}
            />
          </>
        )}

        <TextInput
          style={styles.input}
          placeholder="Telefone"
          value={formData.telefone}
          onChangeText={(t) => handleChange("telefone", t)}
        />

        <Text style={styles.section}>Endereço</Text>

        <TextInput
          style={styles.input}
          placeholder="Logradouro"
          value={formData.logradouro}
          onChangeText={(t) => handleChange("logradouro", t)}
        />

        <TextInput
          style={styles.input}
          placeholder="Número"
          value={formData.numero}
          onChangeText={(t) => handleChange("numero", t)}
        />

        <TextInput
          style={styles.input}
          placeholder="Complemento"
          value={formData.complemento}
          onChangeText={(t) => handleChange("complemento", t)}
        />

        <TextInput
          style={styles.input}
          placeholder="Bairro"
          value={formData.bairro}
          onChangeText={(t) => handleChange("bairro", t)}
        />

        <TextInput
          style={styles.input}
          placeholder="Cidade"
          value={formData.cidade}
          onChangeText={(t) => handleChange("cidade", t)}
        />

        <TextInput
          style={styles.input}
          placeholder="UF"
          maxLength={2}
          value={formData.uf}
          onChangeText={(t) => handleChange("uf", t.toUpperCase())}
        />

        <TextInput
          style={styles.input}
          placeholder="CEP"
          value={formData.cep}
          onChangeText={(t) => handleChange("cep", t)}
        />

      </ScrollView>

      {/* Botões */}
      <View style={styles.buttons}>
        <TouchableOpacity style={styles.save} onPress={submit}>
          <Text style={styles.saveText}>{buttonTitle}</Text>
        </TouchableOpacity>

        <TouchableOpacity style={styles.cancel} onPress={() => navigation.goBack()}>
          <Text style={styles.cancelText}>Cancelar</Text>
        </TouchableOpacity>
      </View>
    </View>
  );
}