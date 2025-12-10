import React, { useState, useEffect } from 'react';
import { 
  View, Text, TextInput, ScrollView, StyleSheet, 
  TouchableOpacity, Alert, Platform
} from 'react-native';

import { Picker } from '@react-native-picker/picker';

const especialidades = [
  'CARDIOLOGIA', 'PEDIATRIA', 'DERMATOLOGIA', 'GINECOLOGIA',
  'NEUROLOGIA', 'OFTALMOLOGIA', 'CLINICA_GERAL'
];

const initialMedicoState = {
  nome: '',
  especialidade: especialidades[0],
  crm: '',
  email: '',
  telefone: '',
  logradouro: '',
  numero: '',
  complemento: '',
  bairro: '',
  cidade: '',
  uf: '',
  cep: '',
};

const MedicoForm = ({ medico, onSave, onCancel, navigation }) => {
  const [formData, setFormData] = useState(medico || initialMedicoState);
  const [errors, setErrors] = useState({});

  const isEditing = !!medico;
  const buttonTitle = isEditing ? "Salvar Alterações" : "Concluir Cadastro";

  useEffect(() => {
    setFormData(medico || initialMedicoState);
  }, [medico]);

  const handleChange = (name, value) => {
    setFormData(prev => ({ ...prev, [name]: value }));
    if (errors[name]) {
      const updated = { ...errors };
      delete updated[name];
      setErrors(updated);
    }
  };

  const validate = () => {
    let ok = true;
    const newErrors = {};

    const required = [
      "nome", "telefone", "logradouro", "numero", 
      "bairro", "cidade", "uf", "cep"
    ];

    if (!isEditing) {
      required.push("email", "crm", "especialidade");
    }

    required.forEach(field => {
      if (!formData[field] || formData[field].trim() === "") {
        newErrors[field] = "Campo obrigatório";
        ok = false;
      }
    });

    if (formData.cep && !/^\d{8}$/.test(formData.cep)) {
      newErrors.cep = "CEP deve ter 8 dígitos";
      ok = false;
    }

    setErrors(newErrors);
    return ok;
  };

  const handleSubmit = () => {
    if (!validate()) {
      Alert.alert("Atenção", "Preencha os campos obrigatórios.");
      return;
    }

    let payload = {};

    if (isEditing) {
      payload = {
        id: medico.id,
        nome: formData.nome,
        telefone: formData.telefone,
        endereco: {
          logradouro: formData.logradouro,
          numero: formData.numero,
          complemento: formData.complemento,
          bairro: formData.bairro,
          cidade: formData.cidade,
          uf: formData.uf.toUpperCase(),
          cep: formData.cep,
        }
      };
    } else {
      payload = {
        nome: formData.nome,
        email: formData.email,
        telefone: formData.telefone,
        crm: formData.crm,
        especialidade: formData.especialidade,
        endereco: {
          logradouro: formData.logradouro,
          numero: formData.numero,
          complemento: formData.complemento,
          bairro: formData.bairro,
          cidade: formData.cidade,
          uf: formData.uf.toUpperCase(),
          cep: formData.cep,
        }
      };
    }

    onSave(payload);
  };

  const ValidatedInput = ({ label, name, ...props }) => (
    <View style={formStyles.inputGroup}>
      <Text style={formStyles.label}>{label}</Text>

      <TextInput
        style={[
          formStyles.input,
          errors[name] && formStyles.inputError
        ]}
        value={String(formData[name] || "")}
        onChangeText={(t) => handleChange(name, t)}
        {...props}
      />
      {errors[name] && (
        <Text style={formStyles.errorText}>{errors[name]}</Text>
      )}
    </View>
  );

  return (
    <View style={styles.container}>
      <ScrollView style={styles.scroll}>
        
        <Text style={styles.title}>
          {isEditing ? "Editar Médico" : "Cadastrar Médico"}
        </Text>

        <ValidatedInput label="Nome" name="nome" />

        {!isEditing && (
          <>
            <Text style={formStyles.label}>Especialidade</Text>
            <View style={formStyles.pickerWrapper}>
              <Picker
                selectedValue={formData.especialidade}
                onValueChange={(v) => handleChange("especialidade", v)}
              >
                {especialidades.map(e => (
                  <Picker.Item key={e} label={e} value={e} />
                ))}
              </Picker>
            </View>

            <ValidatedInput label="CRM" name="crm" />
            <ValidatedInput label="Email" name="email" />
          </>
        )}

        <ValidatedInput label="Telefone" name="telefone" />

        <Text style={styles.section}>Endereço</Text>

        <ValidatedInput label="Logradouro" name="logradouro" />
        <ValidatedInput label="Número" name="numero" />
        <ValidatedInput label="Complemento" name="complemento" />
        <ValidatedInput label="Bairro" name="bairro" />
        <ValidatedInput label="Cidade" name="cidade" />

        <ValidatedInput label="UF" name="uf" maxLength={2} />
        <ValidatedInput label="CEP" name="cep" keyboardType="numeric" maxLength={8} />

      </ScrollView>

      <View style={styles.buttons}>
        <TouchableOpacity style={styles.save} onPress={handleSubmit}>
          <Text style={styles.saveText}>{buttonTitle}</Text>
        </TouchableOpacity>

        <TouchableOpacity style={styles.cancel} onPress={onCancel || navigation.goBack}>
          <Text style={styles.cancelText}>Cancelar</Text>
        </TouchableOpacity>
      </View>
    </View>
  );
};

const styles = StyleSheet.create({
  container: { flex: 1, backgroundColor: "#fff" },
  scroll: { padding: 20 },
  title: { fontSize: 22, fontWeight: "bold", marginBottom: 20 },
  section: { marginTop: 20, fontWeight: "bold", color: "#007AFF" },
  buttons: {
    flexDirection: "row",
    padding: 10,
    borderTopWidth: 1,
    borderColor: "#ddd"
  },
  save: {
    flex: 1, padding: 15, backgroundColor: "#007AFF",
    marginRight: 5, borderRadius: 8, alignItems: "center"
  },
  cancel: {
    flex: 1, padding: 15, backgroundColor: "#6c757d",
    marginLeft: 5, borderRadius: 8, alignItems: "center"
  },
  saveText: { color: "#fff", fontWeight: "bold" },
  cancelText: { color: "#fff", fontWeight: "bold" }
});

const formStyles = StyleSheet.create({
  inputGroup: { marginBottom: 12 },
  label: { fontWeight: "600", marginBottom: 4 },
  input: {
    borderWidth: 1, borderColor: "#ccc",
    backgroundColor: "#f5f5f5",
    borderRadius: 8, padding: 10
  },
  pickerWrapper: {
    borderWidth: 1, borderColor: "#ccc",
    borderRadius: 8, backgroundColor: "#f5f5f5",
    marginBottom: 12
  },
  inputError: {
    borderColor: "red", borderWidth: 2
  },
  errorText: { color: "red", fontSize: 12 }
});

export default MedicoForm;