# Desafio Meet Time

A proposta do Desafio é
Criar um Endpoint, que ao ser chamado pela web,  
ele me redirecione para a página do HubSpot para poder confirmar o acesso ao aplicativo.
- /autenticar

Ao acessar o aplicativo, receberia os tokens de acesso para poder Cadastrar os contatos.
- /contatos/criar

Ao cadastrá-los, eu receberia uma notificação de um webhook do HubSpot.  
- contatos/notificar-novo

## Fluxo das Jornadas
Um desenho simplificado do fluxo idealizado das jornadas.
![meetime_jornada_autorizacao](https://github.com/user-attachments/assets/f74942b0-e515-4349-842b-5b74e81a2893)

![meetime_jornada_contato](https://github.com/user-attachments/assets/7363d376-e7be-41bc-9cb4-a8bfd03a5d45)

## Instruções:
### Projeto Hospedado no Render

O projeto está hospedado sob o endereço:
https://desafiomeetime.onrender.com

- Para o redirecionamento e autorização, chamar a Url:  
https://app.hubspot.com/oauth/authorize?client_id=4e0adbd6-18fb-4534-8081-bea85585bb43&redirect_uri=https://desafiomeetime.onrender.com/autenticar/auth-callback&scope=crm.objects.contacts.write%20crm.schemas.contacts.write%20oauth%20crm.objects.contacts.read

Com ela você conseguirá o response com o bearer token e o refresh token:  
```
{
  "refresh_token": "bf59-bd59-4e6c-985d",
  "access_token": "CLu5yszoMhINAAEAQAAAAQAAAAAAARibg9oXIKPv",
  "expires_in": 1800
}
```

- Curl da chamada para criar um contato:  
```
curl --request POST \
  --url https://desafiomeetime.onrender.com/contatos/criar \
  --header 'Authorization: CLu5yszoMhINAAEAQAAAAQAAAAAAARibg9oXIKPv_iUo9O7OBTIUmlpkG2EH-uSO_UpvCzHqCoEaIwo6MAAAAEEAAAAAAAAAAAAAAAAAgAAAAAAAAAAAACAAAAAAAOABAAAAAAAAAIABAAAQAkIUTdjqpbz9NzUocxzPudoU5pQvW_VKA25hMVIAWgBgAGij7_4lcAA' \
  --header 'Content-Type: application/json' \
  --header 'User-Agent: insomnia/10.0.0' \
  --header 'refresh: na1-bf59-bd59-4e6c-985d-84a420233a94' \
  --data '{
	"email": "joseph@teste.com",
	"lastname": "X.",
	"firstname": "joseph"
}'
```

- Ao conseguir criar contatos e ao receber uma notificação do webhook,  
para facilitar o processo, adicionei um H2 para validar os recebimentos do webhook.  
A lista apenas salva os Id's. Pode ser conferida em:  
https://desafiomeetime.onrender.com/contatos/id-salvo


### Rodar o Projeto Localmente

- O projeto roda na porta 8080  
- Será necessário cadastrar uma conta para Aplicativo e uma conta para teste:  
https://app.hubspot.com/developer/

Na seção de Autenticação do Aplicativo, as seguintes ações serão necessárias.  
- Salvar o Client ID e Secret posteriormente para a aplicação.
- URL de redirecionamento, conforme projeto: localhost:8080/autenticar/auth-callback
- Projeto cadastrou os seguintes escopos:  
```
crm.objects.contacts.read  
crm.objects.contacts.write  
crm.schemas.contacts.write  
oauth  
```
- Na parte de Webhooks o path cadastrado foi: `/contatos/notificar-novo`  
Porém a url, que necessita de uma url real, foi gerada localmente usando ngrok.  
[ Site + Tutorial para instalar: https://ngrok.com/downloads/linux ]  
Ficará algo parecido com:  
https://b339-2804-295c-c100-257c-bf89-8847-ccfe-b576.ngrok-free.app/contatos/notificar-novo

- Criar um arquivo na raiz do projeto chamado: `env.properties`  
Nesse arquivo precisará conter o Client Id e Secret da aplicação, para o login.  
ex:

```
CLIENT_ID=insira o client id
CLIENT_SECRET=insira o client secret
```
  
  
### Com o projeto em Execução

Abrir o navegador e inserir a url:  
https://app.hubspot.com/oauth/authorize?client_id=INSERIR_CLIENT_ID&scope=crm.objects.contacts.write%20crm.schemas.contacts.write%20oauth%20crm.objects.contacts.read&redirect_uri=http%3A%2F%2Flocalhost%3A8080%2Fautenticar%2Fauth-callback

Te redirecionará para uma página, onde você precisará de um perfil de teste criado.  
Você dará permissão para o aplicativo.  
Após conceder a permissão, será direcionada para o endpoint de callback da aplicação.  
Nisso, haverá uma chamada pra conseguir o Bearer Token e o Refresh Token. Ex:  
```
{
  "refresh_token": "bf59-bd59-4e6c-985d",
  "access_token": "CLu5yszoMhINAAEAQAAAAQAAAAAAARibg9oXIKPv",
  "expires_in": 1800
}
```

O Bearer e o Refresh serão úteis para a chamada de criação de contato. Ex de chamada:  
```
curl --request POST \
  --url http://localhost:8080/contatos/criar \
  --header 'Authorization: CLu5yszoMhINAAEAQAAAAQAAAAAAARibg9oXIKPv_iUo9O7OBTIUmlpkG2EH-uSO_UpvCzHqCoEaIwo6MAAAAEEAAAAAAAAAAAAAAAAAgAAAAAAAAAAAACAAAAAAAOABAAAAAAAAAIABAAAQAkIUTdjqpbz9NzUocxzPudoU5pQvW_VKA25hMVIAWgBgAGij7_4lcAA' \
  --header 'Content-Type: application/json' \
  --header 'User-Agent: insomnia/10.0.0' \
  --header 'refresh: na1-bf59-bd59-4e6c-985d-84a420233a94' \
  --data '{
	"email": "joseph@teste.com",
	"lastname": "X.",
	"firstname": "joseph"
}'
```

Ao obter sucesso em criar o contato  
(O mesmo também pode ser consultado na Página do HubSpot em contatos)
O HubSpot enviará uma mensagem via Webhook para poder confirmar a criação de um novo contato.

## Tomadas de Decisões
### Libs
- Open Feign: Considerada a lib padrão para spring cloud. Pareceu a decisão mais assertiva.
- Spring Retry e Starter AOP: Necessárias para identificar o comportamento do Retry da aplicação.
- Wiremock: Poderosa lib no auxílio de gerar stubs para os testes.

### Melhorias  
- Cache: Estou acostumado a cachear toda chamada de token. Como é uma aplicação de teste,  
acabei não utilizando. Apenas aproveitei o refresh token. No entanto, por precisar aproveitar o refresh,  se tornaria necessário eu pensar um pouco a mais em como seria a estratégia do cache.
- Validação API HubSpot: Vi na documentação que existe como validar se o envio foi feito pelo HubSpot,  mas pelo contexto do desafio, somado ao tempo que levaria para entender como fazer, acabou não se provando tão necessário.

