<script lang="ts" setup>
import { ref, watch } from 'vue';
import axios from 'axios';

const termoBusca = ref<string>('');  
const operadoras = ref<Array<{ id: number, nomeFantasia: string }>>([]); 
const carregando = ref<boolean>(false);  
const erro = ref<string | null>(null); 
let timeout: ReturnType<typeof setTimeout> | null = null;  

const buscarOperadoras = async () => {
  if (termoBusca.value.length < 3) {
    operadoras.value = [];
    return;
  }

  carregando.value = true;
  erro.value = null;

  try {
    setTimeout(async () => {
      const resposta = await axios.get(
        `http://localhost:8080/api/v1/operadoras/${termoBusca.value}`
      );
      operadoras.value = resposta.data;
      carregando.value = false;
    }, 3000);
  } catch (err) {
    erro.value = 'Erro ao buscar operadoras';
    carregando.value = false;
  }
};

watch(termoBusca, (novoValor) => {
  if (timeout) clearTimeout(timeout);
  timeout = setTimeout(() => {
    buscarOperadoras();
  }, 500);
});
</script>

<template>
  <div class="container">
    <h1 class="titulo">BUSCA DE OPERADORAS ATIVAS</h1>
    
    <div class="search-container">
      <input v-model="termoBusca" type="text" placeholder="Digite o nome da operadora" class="search-bar" />
      <button @click="buscarOperadoras" class="search-button">
        🔍
      </button>
    </div>

    <div v-if="carregando" class="loading">Carregando...</div>
    
    <div v-if="operadoras.length > 0" class="results-container">
      <div v-for="operadora in operadoras" :key="operadora.id" class="operadora-card aparece">
        <p>{{ operadora.nomeFantasia }}</p>
      </div>
    </div>
    
    <div v-if="erro" class="error">{{ erro }}</div>
  </div>
</template>

<style scoped>
* {
  box-sizing: border-box;
  font-family: Poppins, sans-serif;
}

body {
  background-color: #f4f4f4;
  margin: 0;
  padding: 0;
  display: flex;
  justify-content: center;
  align-items: center;
  height: 100vh;
}

.container {
  text-align: center;
  width: 100%;
  max-width: 500px;
  display: flex;
  flex-direction: column; 
  align-items: center;    
}

.titulo {
  color: #008080;
  font-size: 24px;
  margin-bottom: 20px;
}

.search-container {
  display: flex;
  justify-content: center;
  align-items: center;
  background: white;
  border-radius: 25px;
  padding: 10px;
  box-shadow: 0px 2px 5px rgba(0, 0, 0, 0.1);
  width: 100%;
}

.search-bar {
  width: 100%;
  padding: 12px;
  font-size: 16px;
  border: none;
  outline: none;
  border-radius: 25px;
}

.search-button {
  background: none;
  border: none;
  cursor: pointer;
  font-size: 20px;
  margin-left: 10px;
}

.results-container {
  margin-top: 20px;
}

.operadora-card {
  background: #dfe6e9;
  padding: 15px;
  border-radius: 10px;
  box-shadow: 0px 2px 5px rgba(0, 0, 0, 0.1);
  margin-bottom: 10px;
  font-size: 18px;
  font-weight: bold;
  transition: opacity 0.5s ease, transform 0.3s ease;
  opacity: 0;
  transform: translateY(10px);
}

.operadora-card.aparece {
  opacity: 1;
  transform: translateY(0);
}

.operadora-card:hover {
  background: #b2bec3;
}

.loading {
  margin-top: 20px;
  color: #888;
}

.error {
  color: red;
  margin-top: 20px;
}
</style>