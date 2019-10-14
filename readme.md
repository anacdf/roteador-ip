# Definição do Trabalho

Desenvolver uma aplicação que simule um roteador de rede e que faça a troca de tabelas de   roteamento,   semelhante   ao   que   foi   apresentado   em   aula,   utilizando   sockets   UDP   para   acomunicação. 
A aplicação deve implementar o protocolo descrito a seguir. Inicialmente,   deverá   ser   informado,   na   aplicação,   os   endereços   IPs   dos  roteadoresvizinhos  para que várias topologias diferentes possam ser simuladas. Cada roteador vizinho éuma instância do roteador implementado executando em outra máquina física. Esses endereçosIPs deverão ser cadastrados em uma tabela de roteamento com métrica 1 e saída direta. Trêscampos deverão estar presentes na tabela de roteamento: IP de Destino, Métrica e IP de Saída.As   tabelas   de   roteamento   (apenas   os   campos   IP   de   Destino   e   Métrica)   deverão   sertrocadas entre os roteadores vizinhos a cada 10 segundos. Ao receber a tabela de roteamento deseus vizinhos, a aplicação deverá verificar as rotas recebidas e fazer as atualizações necessáriasna tabela de roteamento local. 
Uma atualização deverá ser feita sempre que: 
- for recebido um IP de Destino não presente na tabela local. Neste caso a rota deveser adicionada, a Métrica deve ser incrementada em 1 e o IP de Saída deve ser o endereço doroteador que ensinou esta informação; 
- for recebida  uma Métrica menor para um IP Destino presente na tabela local.Neste caso, a Métrica e o IP de Saída devem ser atualizadas; 
- um IP Destino deixar de ser divulgado. Neste caso, a rota deve ser retirada databela de roteamento.Um roteador pode sair da rede a qualquer momento. Isso significa que seus vizinhos nãoreceberão mais anúncios de rotas. Assim, depois de 30 segundos sem receber mensagens doroteador vizinho em questão, as rotas que passam por ele devem ser esquecidas. Periodicamente, a tabela de roteamento local deverá ser apresentada para o usuário. Alémdisso, alterações na tabela de roteamento deverão ser informadas para os usuários (através deprints na saída padrão).
A aplicação deverá rodar sobre o protocolo UDP.

# Protocolo de comunicação
A implementação deve respeitar fielmente o formato de mensagens descrito a seguir. A aplicação   resultante   deve   ser  interoperável,   ou   seja,   implementações   de   diferentes   grupos devem ser capazes de se comunicar entre si. Desta maneira, poderá ser construída uma topologia com roteadores implementados por diferentes equipes. O protocolo consiste em apenas duas mensagens, conforme descrito a seguir. 
Mensagem 1 - Anúncio de rotasEsta mensagem deve ser enviada aos vizinhos a cada 10 segundos e consiste no envio databela   de   roteamento   para   os   roteadores   vizinhos.  
A   mensagem   consiste   em   uma   lista   deendereços IP’s em formato string. 
Por exemplo, se a tabela for:IPMétricaSaída192.168.1.21192.168.1.1192.168.1.31192.168.1.1A mensagem enviada será: *192.168.1.2;1*192.168.1.3;1 
Ou seja, “*” (asterisco) indica uma tupla, IP de Destino e Métrica. A métrica é separadado IP por um “;” (ponto e vírgula).O protocolo não prevê confirmação de recebimento de mensagem, pois a tabela deveráser reenviada a cada 10 segundos. Contudo, caso o recebimento de uma mensagem de anúncio derotas cause a alteração da tabela de roteamento, o roteador deve enviar sua tabela imediatamentepara seus vizinhos.Mensagem 2 - Anúncio de roteadorEsta mensagem deverá ser enviada aos vizinhos a cada 10 segundos somente se a tabelade roteamento estiver vazia. Assim, logo que um roteador entrar na rede e não tiver nenhumarota pré-configurada, deverá anunciar-se para os vizinhos. A mensagem enviada será:!
Ou seja, apenas um ponto de exclamação para anunciar sua entrada. Exemplo de topologia com 4 roteadoresA figura abaixo ilustra 4 roteadores e suas respectivas tabelas de roteamento depois dealgumas iterações para troca de tabelas. As flechas indicam roteadores vizinhos. Considerações sobre a implementaçãoA aplicação deve ser multithread, ou seja, devem existir ao menos duas threads: uma parareceber mensagens dos vizinhos e atualizar a tabela de roteamento e outra para enviar a tabela deroteamento para os vizinhos a cada 10 segundos, ou quando a mesma for alterada. Os endereços IP dos roteadores vizinho devem ser informados no arquivo denominadoIPVizinhos.txt (um por linha), conforme implementação de referência.A interoperabilidade é uma questão fundamental em redes de computadores. Ela permiteque   equipamentos   de   diferentes   fabricantes   operem   em   harmonia   na   rede.   Para   isso,   asespecificações dos protocolos devem ser rigidamente implementadas. Espera-se neste trabalho,que as diversas equipes apresentem implementações coerentes com a especificação e que permitaa interoperabilidade com as implementações das outras equipes. Como base para o início da implementação, utilize o esqueleto de código disponível noMoodle. Tal implementação ilustra a comunicação através de sockets UDP.  
As mensagens deanúncio de rotas devem ser enviadas para a porta 5000 do roteador vizinho.

# Regras Gerais

Grupos: Até 3 componentes 
Data de entrega:  08/11
Obs.: Todos participantes devem estar presentes.
Entrega final: 
- Código fonte comentado.
- Descrição do formato do pacote utilizado (token, dados, confirmação).
Visualização dos Resultados:
- A  demonstração  deverá acontecer,  no  mínimo,  em 4 máquinas.  Os  grupos  serão convidados acolocar seus roteadores em uma topologia definida pelo professor e que envolverá diversos grupos. 
IMPORTANTE: Não serão aceitos trabalhos entregues fora do prazo. Trabalhos que não compilam   ou que  não  executam   não  serão  avaliados.   Todos  os  trabalhos   serão analisados   e comparados. Caso seja identificada cópia de trabalhos, todos os trabalhos envolvidos receberão nota ZERO.
