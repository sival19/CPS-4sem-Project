FROM mcr.microsoft.com/dotnet/runtime:3.1-focal AS base
WORKDIR /app

FROM mcr.microsoft.com/dotnet/sdk:3.1-focal AS build
WORKDIR /src
COPY ["AGV.csproj", "./"]
RUN dotnet restore
COPY . .
WORKDIR "/src/."
RUN dotnet build "AGV.csproj" -c Release -o /app/build

FROM build AS publish
RUN dotnet publish "AGV.csproj" -c Release -o /app/publish

FROM base AS final
WORKDIR /app
COPY --from=publish /app/publish .
ENTRYPOINT ["dotnet", "AGV.dll"]