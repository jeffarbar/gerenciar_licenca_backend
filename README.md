# gerenciar_licenca_backend


deploy

https://learn.microsoft.com/pt-br/azure/developer/java/spring-framework/deploy-spring-boot-java-app-on-kubernetes

az acr login && mvn compile jib:build


##################################################################




az config set defaults.acr=acrgdvivo
az acr login




loadBalancerIP: 20.84.12.210


az network public-ip create \
    --resource-group MC_rg-gdvivo_aks-gdvivo_eastus \
    --name aksPublicIPFrontend \
    --sku Standard \
	--location eastus \
    --allocation-method static 



az network public-ip create \
    --resource-group MC_rg-gdvivo_aks-gdvivo_eastus \
    --name aksPublicIPBackend \
    --sku Standard \
	--location eastus \
    --allocation-method static 	
	
	
az network public-ip update -g MC_rg-gdvivo_aks-gdvivo_eastus -n aksPublicIPFrontend --dns-prefix gdvivo.com.br --allocation-method Static
	
	gdvivo.com.br
	
	loadBalancerIP: 23.100.19.187
	
	
	az network public-ip show --resource-group rg-gdvivo --name aksPublicIPBack --query ipAddress --output tsv
	
	az network public-ip show --resource-group MC_myResourceGroup_myAKSCluster_eastus --name myAKSPublicIP --query ipAddress --output tsv
	
	
apiVersion: v1
kind: Service
metadata:
  name: gs-backend
spec:
  loadBalancerIP: 74.235.31.64
  type: LoadBalancer
  ports:
  - port: 8090
    targetPort: 8090
  selector:
    app: gs-backend
	
	
	
	az role assignment create \
    --assignee c1f48ecb-fe64-4323-b898-e9b5fdee1c8a \
    --role "Network Contributor" \
    --scope /subscriptions/c1f48ecb-fe64-4323-b898-e9b5fdee1c8a/resourceGroups/rg-gdvivo/providers/Microsoft.ContainerService/managedClusters/aks-gdvivo
	
	
	
	
	az role assignment create \
    --assignee <Client ID> \
    --role "Network Contributor" \
    --scope /subscriptions/<subscription id>/resourceGroups/<MC_myResourceGroup_myAKSCluster_eastus>
	
	
	
	
	
	
az aks update -n aks-gdvivo -g rg-gdvivo --attach-acr /subscriptions/c1f48ecb-fe64-4323-b898-e9b5fdee1c8a/resourceGroups/rg-gdvivo/providers/Microsoft.ContainerRegistry/registries/acrgdvivo

	
	
az aks create --resource-group= \
 --name=aks-gdvivo \
 --attach-acr acrgdvivo \
 --dns-name-prefix=gdvivo-kubernetes --generate-ssh-keys
 
 
 ## subir imagem frontend
 az acr build --image gs-frontend:latest --registry acrgdvivo --file Dockerfile .

## subir imagem do backend
 az acr login && mvn compile jib:build
 
 
