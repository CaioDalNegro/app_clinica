// Tela de listagem dos pacientes, com busca, agrupamento por letra e animações

import React, { useState, useMemo, useEffect } from 'react';
import {
  View, Text, TextInput, StyleSheet, SectionList,
  TouchableOpacity, Platform, LayoutAnimation, UIManager,
  Button, Image
} from 'react-native';

import api from '../../services/api';
import { useIsFocused } from '@react-navigation/native';

// Ícones locais
const IconeLupa = require('../../../assets/lupa.png');
const IconeSeta = require('../../../assets/seta.png');

// Ativa animações suaves no Android
if (Platform.OS === 'android') {
  UIManager.setLayoutAnimationEnabledExperimental &&
    UIManager.setLayoutAnimationEnabledExperimental(true);
}

// ====================================================================
// Função que filtra pacientes pelo nome/cpf e agrupa pela 1ª letra
// ====================================================================
const groupAndFilterPacientes = (pacientes, searchText) => {
  
  // Filtragem por nome ou CPF
  const filtered = pacientes.filter(p =>
    p.nome.toLowerCase().includes(searchText.toLowerCase()) ||
    p.cpf.toLowerCase().includes(searchText.toLowerCase())
  );

  // Agrupa pela primeira letra do nome
  const grouped = filtered.reduce((acc, p) => {
    const firstLetter = p.nome[0].toUpperCase();
    if (!acc[firstLetter]) acc[firstLetter] = [];
    acc[firstLetter].push(p);
    return acc;
  }, {});

  // Ordena as letras e monta as sections
  return Object.keys(grouped)
    .sort()
    .map(letter => ({
      title: letter,
      data: grouped[letter],
    }));
};

// ====================================================================
// Card do paciente — abre ao clicar (accordion)
// ====================================================================
const PacienteCard = ({ paciente, navigation }) => {

  // Controla se o card está expandido ou não
  const [isExpanded, setIsExpanded] = useState(false);

  const toggleExpand = () => {
    LayoutAnimation.configureNext(LayoutAnimation.Presets.easeInEaseOut);
    setIsExpanded(!isExpanded);
  };

  return (
    <View style={cardStyles.card}>

      {/* Cabeçalho do card */}
      <TouchableOpacity onPress={toggleExpand} style={cardStyles.mainInfo}>
        <View>
          <Text style={cardStyles.nome}>{paciente.nome}</Text>
          <Text style={cardStyles.subInfo}>CPF: {paciente.cpf}</Text>
        </View>

        <Image
          source={IconeSeta}
          style={[
            cardStyles.arrowIcon,
            { transform: [{ rotate: isExpanded ? '90deg' : '0deg' }] },
          ]}
        />
      </TouchableOpacity>

      {/* Corpo do card (mostrado só quando expandido) */}
      {isExpanded && (
        <View style={cardStyles.details}>
          <Text style={cardStyles.detailText}>Email: {paciente.email}</Text>
          <Text style={cardStyles.detailText}>Telefone: {paciente.telefone}</Text>

          {/* Endereço somente se existir */}
          {paciente.endereco && (
            <Text style={cardStyles.detailText}>
              Endereço: {paciente.endereco.logradouro}, {paciente.endereco.cidade}
            </Text>
          )}

          <View style={cardStyles.actionButtons}>
            <Button
              title="Editar"
              onPress={() =>
                navigation.navigate("CadastroEdicaoPacienteScreen", { paciente })
              }
            />

            <Button
              title="Desativar"
              color="red"
              onPress={() => navigation.navigate("EmConstrucao")}
            />
          </View>
        </View>
      )}
    </View>
  );
};

// ====================================================================
// Tela principal (lista)
// ====================================================================
const Paciente = ({ navigation }) => {
  
  const [pacientes, setPacientes] = useState([]);
  const [searchText, setSearchText] = useState('');

  const isFocused = useIsFocused();

  // Atualiza ao focar na tela
  useEffect(() => {
    if (isFocused) carregarPacientes();
  }, [isFocused]);

  // Busca pacientes no backend
  const carregarPacientes = async () => {
    try {
      const resp = await api.get("/pacientes?size=100");

      // Lista base da paginação
      const lista = resp.data.content;

      // Busca detalhes completos (porque sua API devolve dados resumidos)
      const detalhes = await Promise.all(
        lista.map(async (p) => {
          const r = await api.get(`/pacientes/${p.id}`);
          return r.data;
        })
      );

      setPacientes(detalhes);
    } catch (e) {
      console.log("Erro ao carregar pacientes:", e);
    }
  };

  // Computa sections da SectionList
  const sections = useMemo(
    () => groupAndFilterPacientes(pacientes, searchText),
    [pacientes, searchText]
  );

  return (
    <View style={styles.container}>

      {/* Barra de busca */}
      <View style={styles.searchContainer}>
        <TextInput
          style={styles.searchInput}
          placeholder="Pesquisar Nome ou CPF"
          value={searchText}
          onChangeText={setSearchText}
        />
        <Image source={IconeLupa} style={styles.searchIcon} />
      </View>

      {/* Lista agrupada */}
      <View style={styles.listWrapper}>
        <SectionList
          sections={sections}
          keyExtractor={(item) => item.id.toString()}
          renderItem={({ item }) => (
            <PacienteCard paciente={item} navigation={navigation} />
          )}
          renderSectionHeader={({ section: { title } }) => (
            <Text style={styles.sectionHeader}>{title}</Text>
          )}
        />
      </View>

      {/* Botão para cadastro */}
      <View style={styles.fixedButtonContainer}>
        <Button
          title="Cadastrar Novo Paciente"
          onPress={() => navigation.navigate("CadastroEdicaoPacienteScreen")}
        />
      </View>

    </View>
  );
};

export default Paciente;