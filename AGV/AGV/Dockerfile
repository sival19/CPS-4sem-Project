FROM mcr.microsoft.com/dotnet/core/runtime:3.1 AS base
WORKDIR /app

FROM mcr.microsoft.com/dotnet/core/sdk:3.1 AS build
WORKDIR /src
COPY ["AGV/AGV.csproj", "AGV/"]
RUN dotnet restore "AGV/AGV.csproj"
COPY . .
WORKDIR "/src/AGV"
RUN dotnet build "AGV.csproj" -c Release -o /app/build

FROM build AS publish
RUN dotnet publish "AGV.csproj" -c Release -o /app/publish

FROM base AS final
WORKDIR /app
COPY --from=publish /app/publish .
ENTRYPOINT ["dotnet", "AGV.dll"]
