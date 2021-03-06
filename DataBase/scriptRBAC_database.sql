USE [master]
GO
/****** Object:  Database [RBAC2]    Script Date: 3/18/2019 8:23:39 PM ******/
CREATE DATABASE [RBAC2]
 CONTAINMENT = NONE
 ON  PRIMARY 
( NAME = N'RBAC2', FILENAME = N'C:\Program Files\Microsoft SQL Server\MSSQL14.SQLEXPRESS\MSSQL\DATA\RBAC2.mdf' , SIZE = 8192KB , MAXSIZE = UNLIMITED, FILEGROWTH = 65536KB )
 LOG ON 
( NAME = N'RBAC2_log', FILENAME = N'C:\Program Files\Microsoft SQL Server\MSSQL14.SQLEXPRESS\MSSQL\DATA\RBAC2_log.ldf' , SIZE = 8192KB , MAXSIZE = 2048GB , FILEGROWTH = 65536KB )
GO
ALTER DATABASE [RBAC2] SET COMPATIBILITY_LEVEL = 140
GO
IF (1 = FULLTEXTSERVICEPROPERTY('IsFullTextInstalled'))
begin
EXEC [RBAC2].[dbo].[sp_fulltext_database] @action = 'enable'
end
GO
ALTER DATABASE [RBAC2] SET ANSI_NULL_DEFAULT OFF 
GO
ALTER DATABASE [RBAC2] SET ANSI_NULLS OFF 
GO
ALTER DATABASE [RBAC2] SET ANSI_PADDING OFF 
GO
ALTER DATABASE [RBAC2] SET ANSI_WARNINGS OFF 
GO
ALTER DATABASE [RBAC2] SET ARITHABORT OFF 
GO
ALTER DATABASE [RBAC2] SET AUTO_CLOSE ON 
GO
ALTER DATABASE [RBAC2] SET AUTO_SHRINK OFF 
GO
ALTER DATABASE [RBAC2] SET AUTO_UPDATE_STATISTICS ON 
GO
ALTER DATABASE [RBAC2] SET CURSOR_CLOSE_ON_COMMIT OFF 
GO
ALTER DATABASE [RBAC2] SET CURSOR_DEFAULT  GLOBAL 
GO
ALTER DATABASE [RBAC2] SET CONCAT_NULL_YIELDS_NULL OFF 
GO
ALTER DATABASE [RBAC2] SET NUMERIC_ROUNDABORT OFF 
GO
ALTER DATABASE [RBAC2] SET QUOTED_IDENTIFIER OFF 
GO
ALTER DATABASE [RBAC2] SET RECURSIVE_TRIGGERS OFF 
GO
ALTER DATABASE [RBAC2] SET  ENABLE_BROKER 
GO
ALTER DATABASE [RBAC2] SET AUTO_UPDATE_STATISTICS_ASYNC OFF 
GO
ALTER DATABASE [RBAC2] SET DATE_CORRELATION_OPTIMIZATION OFF 
GO
ALTER DATABASE [RBAC2] SET TRUSTWORTHY OFF 
GO
ALTER DATABASE [RBAC2] SET ALLOW_SNAPSHOT_ISOLATION OFF 
GO
ALTER DATABASE [RBAC2] SET PARAMETERIZATION SIMPLE 
GO
ALTER DATABASE [RBAC2] SET READ_COMMITTED_SNAPSHOT OFF 
GO
ALTER DATABASE [RBAC2] SET HONOR_BROKER_PRIORITY OFF 
GO
ALTER DATABASE [RBAC2] SET RECOVERY SIMPLE 
GO
ALTER DATABASE [RBAC2] SET  MULTI_USER 
GO
ALTER DATABASE [RBAC2] SET PAGE_VERIFY CHECKSUM  
GO
ALTER DATABASE [RBAC2] SET DB_CHAINING OFF 
GO
ALTER DATABASE [RBAC2] SET FILESTREAM( NON_TRANSACTED_ACCESS = OFF ) 
GO
ALTER DATABASE [RBAC2] SET TARGET_RECOVERY_TIME = 60 SECONDS 
GO
ALTER DATABASE [RBAC2] SET DELAYED_DURABILITY = DISABLED 
GO
ALTER DATABASE [RBAC2] SET QUERY_STORE = OFF
GO
USE [RBAC2]
GO
ALTER DATABASE SCOPED CONFIGURATION SET IDENTITY_CACHE = ON;
GO
ALTER DATABASE SCOPED CONFIGURATION SET LEGACY_CARDINALITY_ESTIMATION = OFF;
GO
ALTER DATABASE SCOPED CONFIGURATION FOR SECONDARY SET LEGACY_CARDINALITY_ESTIMATION = PRIMARY;
GO
ALTER DATABASE SCOPED CONFIGURATION SET MAXDOP = 0;
GO
ALTER DATABASE SCOPED CONFIGURATION FOR SECONDARY SET MAXDOP = PRIMARY;
GO
ALTER DATABASE SCOPED CONFIGURATION SET PARAMETER_SNIFFING = ON;
GO
ALTER DATABASE SCOPED CONFIGURATION FOR SECONDARY SET PARAMETER_SNIFFING = PRIMARY;
GO
ALTER DATABASE SCOPED CONFIGURATION SET QUERY_OPTIMIZER_HOTFIXES = OFF;
GO
ALTER DATABASE SCOPED CONFIGURATION FOR SECONDARY SET QUERY_OPTIMIZER_HOTFIXES = PRIMARY;
GO
USE [RBAC2]
GO
/****** Object:  Table [dbo].[Actions]    Script Date: 3/18/2019 8:23:39 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Actions](
	[ActionID] [int] IDENTITY(1,1) NOT NULL,
	[ActionName] [varchar](255) NOT NULL,
PRIMARY KEY CLUSTERED 
(
	[ActionID] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[ActionSetDetails]    Script Date: 3/18/2019 8:23:39 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[ActionSetDetails](
	[ActionID] [int] NOT NULL,
	[ActionSetID] [int] NOT NULL,
PRIMARY KEY CLUSTERED 
(
	[ActionID] ASC,
	[ActionSetID] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[ActionSets]    Script Date: 3/18/2019 8:23:39 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[ActionSets](
	[ActionSetID] [int] IDENTITY(1,1) NOT NULL,
	[ActionSetName] [varchar](255) NOT NULL,
PRIMARY KEY CLUSTERED 
(
	[ActionSetID] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[ConflictedActions]    Script Date: 3/18/2019 8:23:39 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[ConflictedActions](
	[ActionID1] [int] NOT NULL,
	[ACtionID2] [int] NOT NULL
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[ObjectContainerDetails]    Script Date: 3/18/2019 8:23:39 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[ObjectContainerDetails](
	[ObjectID] [int] NOT NULL,
	[ContainerID] [int] NOT NULL,
PRIMARY KEY CLUSTERED 
(
	[ObjectID] ASC,
	[ContainerID] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[ObjectContainers]    Script Date: 3/18/2019 8:23:40 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[ObjectContainers](
	[ContainerID] [int] IDENTITY(1,1) NOT NULL,
	[ContainerName] [varchar](255) NOT NULL,
PRIMARY KEY CLUSTERED 
(
	[ContainerID] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[Objects]    Script Date: 3/18/2019 8:23:40 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Objects](
	[ObjectID] [int] IDENTITY(1,1) NOT NULL,
	[ObjectName] [varchar](255) NOT NULL,
	[FromDay] [varchar](255) NOT NULL,
	[ToDay] [varchar](255) NOT NULL,
	[FromTime] [varchar](255) NOT NULL,
	[ToTime] [varchar](255) NOT NULL,
	[IpAddress] [varchar](255) NOT NULL,
PRIMARY KEY CLUSTERED 
(
	[ObjectID] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[Permissions]    Script Date: 3/18/2019 8:23:40 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Permissions](
	[PermissionID] [int] IDENTITY(1,1) NOT NULL,
	[ObjectID] [int] NOT NULL,
	[ActionID] [int] NOT NULL,
	[addedtime] [timestamp] NOT NULL,
PRIMARY KEY CLUSTERED 
(
	[ObjectID] ASC,
	[ActionID] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[Roles]    Script Date: 3/18/2019 8:23:40 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Roles](
	[RoleID] [int] IDENTITY(1,1) NOT NULL,
	[RoleName] [varchar](255) NOT NULL,
	[FromDay] [varchar](255) NOT NULL,
	[ToDay] [varchar](255) NOT NULL,
	[FromTime] [varchar](255) NOT NULL,
	[ToTime] [varchar](255) NOT NULL,
	[IpAddress] [varchar](255) NOT NULL,
PRIMARY KEY CLUSTERED 
(
	[RoleID] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[Users]    Script Date: 3/18/2019 8:23:40 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Users](
	[Username] [varchar](255) NOT NULL,
	[Password] [varchar](255) NOT NULL,
	[FromDay] [varchar](255) NOT NULL,
	[ToDay] [varchar](255) NOT NULL,
	[FromTime] [varchar](255) NOT NULL,
	[ToTime] [varchar](255) NOT NULL,
	[IpAddress] [varchar](255) NOT NULL,
PRIMARY KEY CLUSTERED 
(
	[Username] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
SET IDENTITY_INSERT [dbo].[Actions] ON 

INSERT [dbo].[Actions] ([ActionID], [ActionName]) VALUES (10, N'actiontest02')
INSERT [dbo].[Actions] ([ActionID], [ActionName]) VALUES (9, N'ActionTest1')
INSERT [dbo].[Actions] ([ActionID], [ActionName]) VALUES (6, N'Approve')
INSERT [dbo].[Actions] ([ActionID], [ActionName]) VALUES (3, N'Edit')
INSERT [dbo].[Actions] ([ActionID], [ActionName]) VALUES (1, N'Read')
INSERT [dbo].[Actions] ([ActionID], [ActionName]) VALUES (5, N'Remove')
INSERT [dbo].[Actions] ([ActionID], [ActionName]) VALUES (4, N'Rename')
INSERT [dbo].[Actions] ([ActionID], [ActionName]) VALUES (7, N'Submit')
INSERT [dbo].[Actions] ([ActionID], [ActionName]) VALUES (2, N'Write')
SET IDENTITY_INSERT [dbo].[Actions] OFF
INSERT [dbo].[ActionSetDetails] ([ActionID], [ActionSetID]) VALUES (1, 1)
INSERT [dbo].[ActionSetDetails] ([ActionID], [ActionSetID]) VALUES (1, 2)
INSERT [dbo].[ActionSetDetails] ([ActionID], [ActionSetID]) VALUES (2, 3)
INSERT [dbo].[ActionSetDetails] ([ActionID], [ActionSetID]) VALUES (3, 1)
INSERT [dbo].[ActionSetDetails] ([ActionID], [ActionSetID]) VALUES (4, 2)
INSERT [dbo].[ActionSetDetails] ([ActionID], [ActionSetID]) VALUES (4, 3)
INSERT [dbo].[ActionSetDetails] ([ActionID], [ActionSetID]) VALUES (5, 2)
INSERT [dbo].[ActionSetDetails] ([ActionID], [ActionSetID]) VALUES (6, 1)
INSERT [dbo].[ActionSetDetails] ([ActionID], [ActionSetID]) VALUES (7, 3)
SET IDENTITY_INSERT [dbo].[ActionSets] ON 

INSERT [dbo].[ActionSets] ([ActionSetID], [ActionSetName]) VALUES (1, N'Set123')
INSERT [dbo].[ActionSets] ([ActionSetID], [ActionSetName]) VALUES (2, N'Set345')
INSERT [dbo].[ActionSets] ([ActionSetID], [ActionSetName]) VALUES (3, N'Set5678')
SET IDENTITY_INSERT [dbo].[ActionSets] OFF
INSERT [dbo].[ConflictedActions] ([ActionID1], [ACtionID2]) VALUES (7, 6)
INSERT [dbo].[ConflictedActions] ([ActionID1], [ACtionID2]) VALUES (10, 9)
INSERT [dbo].[ObjectContainerDetails] ([ObjectID], [ContainerID]) VALUES (1, 2)
INSERT [dbo].[ObjectContainerDetails] ([ObjectID], [ContainerID]) VALUES (2, 1)
INSERT [dbo].[ObjectContainerDetails] ([ObjectID], [ContainerID]) VALUES (3, 2)
INSERT [dbo].[ObjectContainerDetails] ([ObjectID], [ContainerID]) VALUES (4, 1)
INSERT [dbo].[ObjectContainerDetails] ([ObjectID], [ContainerID]) VALUES (5, 2)
INSERT [dbo].[ObjectContainerDetails] ([ObjectID], [ContainerID]) VALUES (6, 1)
INSERT [dbo].[ObjectContainerDetails] ([ObjectID], [ContainerID]) VALUES (7, 2)
INSERT [dbo].[ObjectContainerDetails] ([ObjectID], [ContainerID]) VALUES (8, 1)
INSERT [dbo].[ObjectContainerDetails] ([ObjectID], [ContainerID]) VALUES (9, 2)
INSERT [dbo].[ObjectContainerDetails] ([ObjectID], [ContainerID]) VALUES (10, 1)
INSERT [dbo].[ObjectContainerDetails] ([ObjectID], [ContainerID]) VALUES (11, 2)
INSERT [dbo].[ObjectContainerDetails] ([ObjectID], [ContainerID]) VALUES (12, 1)
INSERT [dbo].[ObjectContainerDetails] ([ObjectID], [ContainerID]) VALUES (13, 2)
SET IDENTITY_INSERT [dbo].[ObjectContainers] ON 

INSERT [dbo].[ObjectContainers] ([ContainerID], [ContainerName]) VALUES (1, N'ContainerEven')
INSERT [dbo].[ObjectContainers] ([ContainerID], [ContainerName]) VALUES (2, N'ContainerOdd')
SET IDENTITY_INSERT [dbo].[ObjectContainers] OFF
SET IDENTITY_INSERT [dbo].[Objects] ON 

INSERT [dbo].[Objects] ([ObjectID], [ObjectName], [FromDay], [ToDay], [FromTime], [ToTime], [IpAddress]) VALUES (1, N'Obj1', N'MONDAY', N'SUNDAY', N'00:00:01 AM', N'11:59:59 PM', N'192.168.1.181')
INSERT [dbo].[Objects] ([ObjectID], [ObjectName], [FromDay], [ToDay], [FromTime], [ToTime], [IpAddress]) VALUES (2, N'Obj2', N'MONDAY', N'SUNDAY', N'00:00:01 AM', N'11:59:59 PM', N'192.168.1.181')
INSERT [dbo].[Objects] ([ObjectID], [ObjectName], [FromDay], [ToDay], [FromTime], [ToTime], [IpAddress]) VALUES (3, N'Obj3', N'MONDAY', N'SUNDAY', N'10:40:22 AM', N'10:40:22 AM', N'192.168.1.22')
INSERT [dbo].[Objects] ([ObjectID], [ObjectName], [FromDay], [ToDay], [FromTime], [ToTime], [IpAddress]) VALUES (4, N'Obj4', N'MONDAY', N'SUNDAY', N'10:40:22 AM', N'10:40:22 AM', N'192.168.1.22')
INSERT [dbo].[Objects] ([ObjectID], [ObjectName], [FromDay], [ToDay], [FromTime], [ToTime], [IpAddress]) VALUES (5, N'Obj5', N'MONDAY', N'SUNDAY', N'00:00:01 AM', N'11:59:59 PM', N'192.168.1.181')
INSERT [dbo].[Objects] ([ObjectID], [ObjectName], [FromDay], [ToDay], [FromTime], [ToTime], [IpAddress]) VALUES (6, N'Obj6', N'MONDAY', N'SUNDAY', N'00:00:01 AM', N'11:59:59 PM', N'192.168.1.181')
INSERT [dbo].[Objects] ([ObjectID], [ObjectName], [FromDay], [ToDay], [FromTime], [ToTime], [IpAddress]) VALUES (7, N'Obj7', N'MONDAY', N'SUNDAY', N'10:40:22 AM', N'10:40:22 AM', N'192.168.1.22')
INSERT [dbo].[Objects] ([ObjectID], [ObjectName], [FromDay], [ToDay], [FromTime], [ToTime], [IpAddress]) VALUES (8, N'Obj8', N'MONDAY', N'SUNDAY', N'10:40:22 AM', N'10:40:22 AM', N'192.168.1.22')
INSERT [dbo].[Objects] ([ObjectID], [ObjectName], [FromDay], [ToDay], [FromTime], [ToTime], [IpAddress]) VALUES (9, N'Obj9', N'MONDAY', N'SUNDAY', N'10:40:22 AM', N'10:40:22 AM', N'192.168.1.22')
INSERT [dbo].[Objects] ([ObjectID], [ObjectName], [FromDay], [ToDay], [FromTime], [ToTime], [IpAddress]) VALUES (10, N'Obj10', N'MONDAY', N'SUNDAY', N'10:40:22 AM', N'10:40:22 AM', N'192.168.1.22')
INSERT [dbo].[Objects] ([ObjectID], [ObjectName], [FromDay], [ToDay], [FromTime], [ToTime], [IpAddress]) VALUES (11, N'Obj11', N'MONDAY', N'SUNDAY', N'10:40:22 AM', N'10:40:22 AM', N'192.168.1.22')
INSERT [dbo].[Objects] ([ObjectID], [ObjectName], [FromDay], [ToDay], [FromTime], [ToTime], [IpAddress]) VALUES (12, N'Obj12', N'MONDAY', N'SUNDAY', N'10:40:22 AM', N'10:40:22 AM', N'192.168.1.22')
INSERT [dbo].[Objects] ([ObjectID], [ObjectName], [FromDay], [ToDay], [FromTime], [ToTime], [IpAddress]) VALUES (13, N'Obj13', N'MONDAY', N'SUNDAY', N'10:40:22 AM', N'10:40:22 AM', N'192.168.1.22')
INSERT [dbo].[Objects] ([ObjectID], [ObjectName], [FromDay], [ToDay], [FromTime], [ToTime], [IpAddress]) VALUES (15, N'obj14', N'SUNDAY', N'SUNDAY', N'04:02:26 PM', N'11:02:26 PM', N'192.168.1.165')
INSERT [dbo].[Objects] ([ObjectID], [ObjectName], [FromDay], [ToDay], [FromTime], [ToTime], [IpAddress]) VALUES (16, N'obj15', N'TUESDAY', N'SUNDAY', N'01:08:41 PM', N'12:08:41 AM', N'192.168.1.2')
INSERT [dbo].[Objects] ([ObjectID], [ObjectName], [FromDay], [ToDay], [FromTime], [ToTime], [IpAddress]) VALUES (17, N'obj16', N'SUNDAY', N'SUNDAY', N'08:12:32 PM', N'08:12:32 PM', N'192.168.1.3')
SET IDENTITY_INSERT [dbo].[Objects] OFF
SET IDENTITY_INSERT [dbo].[Permissions] ON 

INSERT [dbo].[Permissions] ([PermissionID], [ObjectID], [ActionID]) VALUES (29, 1, 1)
INSERT [dbo].[Permissions] ([PermissionID], [ObjectID], [ActionID]) VALUES (28, 1, 3)
INSERT [dbo].[Permissions] ([PermissionID], [ObjectID], [ActionID]) VALUES (27, 1, 6)
INSERT [dbo].[Permissions] ([PermissionID], [ObjectID], [ActionID]) VALUES (30, 1, 7)
INSERT [dbo].[Permissions] ([PermissionID], [ObjectID], [ActionID]) VALUES (1, 2, 2)
INSERT [dbo].[Permissions] ([PermissionID], [ObjectID], [ActionID]) VALUES (26, 2, 3)
INSERT [dbo].[Permissions] ([PermissionID], [ObjectID], [ActionID]) VALUES (7, 2, 4)
INSERT [dbo].[Permissions] ([PermissionID], [ObjectID], [ActionID]) VALUES (13, 2, 7)
INSERT [dbo].[Permissions] ([PermissionID], [ObjectID], [ActionID]) VALUES (2, 4, 2)
INSERT [dbo].[Permissions] ([PermissionID], [ObjectID], [ActionID]) VALUES (8, 4, 4)
INSERT [dbo].[Permissions] ([PermissionID], [ObjectID], [ActionID]) VALUES (14, 4, 7)
INSERT [dbo].[Permissions] ([PermissionID], [ObjectID], [ActionID]) VALUES (3, 6, 2)
INSERT [dbo].[Permissions] ([PermissionID], [ObjectID], [ActionID]) VALUES (9, 6, 4)
INSERT [dbo].[Permissions] ([PermissionID], [ObjectID], [ActionID]) VALUES (15, 6, 7)
INSERT [dbo].[Permissions] ([PermissionID], [ObjectID], [ActionID]) VALUES (4, 8, 2)
INSERT [dbo].[Permissions] ([PermissionID], [ObjectID], [ActionID]) VALUES (10, 8, 4)
INSERT [dbo].[Permissions] ([PermissionID], [ObjectID], [ActionID]) VALUES (16, 8, 7)
INSERT [dbo].[Permissions] ([PermissionID], [ObjectID], [ActionID]) VALUES (5, 10, 2)
INSERT [dbo].[Permissions] ([PermissionID], [ObjectID], [ActionID]) VALUES (11, 10, 4)
INSERT [dbo].[Permissions] ([PermissionID], [ObjectID], [ActionID]) VALUES (17, 10, 7)
INSERT [dbo].[Permissions] ([PermissionID], [ObjectID], [ActionID]) VALUES (6, 12, 2)
INSERT [dbo].[Permissions] ([PermissionID], [ObjectID], [ActionID]) VALUES (12, 12, 4)
INSERT [dbo].[Permissions] ([PermissionID], [ObjectID], [ActionID]) VALUES (18, 12, 7)
SET IDENTITY_INSERT [dbo].[Permissions] OFF
SET IDENTITY_INSERT [dbo].[Roles] ON 

INSERT [dbo].[Roles] ([RoleID], [RoleName], [FromDay], [ToDay], [FromTime], [ToTime], [IpAddress]) VALUES (1, N'Role1', N'MONDAY', N'MONDAY', N'10:40:22 AM', N'10:40:22 AM', N'192.168.1.22')
INSERT [dbo].[Roles] ([RoleID], [RoleName], [FromDay], [ToDay], [FromTime], [ToTime], [IpAddress]) VALUES (2, N'Role2', N'MONDAY', N'SUNDAY', N'10:40:22 AM', N'10:40:22 AM', N'192.168.1.22')
INSERT [dbo].[Roles] ([RoleID], [RoleName], [FromDay], [ToDay], [FromTime], [ToTime], [IpAddress]) VALUES (3, N'Role3', N'SUNDAY', N'FRIDAY', N'00:00:02 AM', N'11:59:59 PM', N'192.168.1.181')
INSERT [dbo].[Roles] ([RoleID], [RoleName], [FromDay], [ToDay], [FromTime], [ToTime], [IpAddress]) VALUES (4, N'Role4', N'SUNDAY', N'SUNDAY', N'10:40:22 AM', N'10:40:22 AM', N'192.168.1.22')
INSERT [dbo].[Roles] ([RoleID], [RoleName], [FromDay], [ToDay], [FromTime], [ToTime], [IpAddress]) VALUES (5, N'Role5', N'MONDAY', N'SUNDAY', N'10:40:22 AM', N'10:40:22 AM', N'192.168.1.22')
SET IDENTITY_INSERT [dbo].[Roles] OFF
INSERT [dbo].[Users] ([Username], [Password], [FromDay], [ToDay], [FromTime], [ToTime], [IpAddress]) VALUES (N'Admin', N'1234', N'MONDAY', N'SUNDAY', N'00:00:01 AM', N'11:59:00 PM', N'192.168.1.181')
INSERT [dbo].[Users] ([Username], [Password], [FromDay], [ToDay], [FromTime], [ToTime], [IpAddress]) VALUES (N'hulala', N'123456', N'MONDAY', N'SUNDAY', N'01:40:22 AM', N'01:20:22 AM', N'192.168.1.181')
INSERT [dbo].[Users] ([Username], [Password], [FromDay], [ToDay], [FromTime], [ToTime], [IpAddress]) VALUES (N'nam567', N'12345678', N'MONDAY', N'SUNDAY', N'01:40:22 AM', N'11:40:22 PM', N'192.168.4.6')
INSERT [dbo].[Users] ([Username], [Password], [FromDay], [ToDay], [FromTime], [ToTime], [IpAddress]) VALUES (N'namvn12', N'12345678', N'MONDAY', N'SUNDAY', N'01:40:22 AM', N'11:40:22 PM', N'192.168.4.6')
INSERT [dbo].[Users] ([Username], [Password], [FromDay], [ToDay], [FromTime], [ToTime], [IpAddress]) VALUES (N'olala', N'123456', N'MONDAY', N'WEDNESDAY', N'01:40:22 AM', N'11:40:25 PM', N'192.168.1.6')
SET ANSI_PADDING ON
GO
/****** Object:  Index [UQ__Actions__491F5DD0E4534339]    Script Date: 3/18/2019 8:23:40 PM ******/
ALTER TABLE [dbo].[Actions] ADD UNIQUE NONCLUSTERED 
(
	[ActionName] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, SORT_IN_TEMPDB = OFF, IGNORE_DUP_KEY = OFF, ONLINE = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
GO
SET ANSI_PADDING ON
GO
/****** Object:  Index [UQ__ActionSe__491F5DD0133B3CE4]    Script Date: 3/18/2019 8:23:40 PM ******/
ALTER TABLE [dbo].[ActionSets] ADD UNIQUE NONCLUSTERED 
(
	[ActionSetName] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, SORT_IN_TEMPDB = OFF, IGNORE_DUP_KEY = OFF, ONLINE = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
GO
SET ANSI_PADDING ON
GO
/****** Object:  Index [UQ__ObjectCo__C59A582F33E65079]    Script Date: 3/18/2019 8:23:40 PM ******/
ALTER TABLE [dbo].[ObjectContainers] ADD UNIQUE NONCLUSTERED 
(
	[ContainerName] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, SORT_IN_TEMPDB = OFF, IGNORE_DUP_KEY = OFF, ONLINE = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
GO
SET ANSI_PADDING ON
GO
/****** Object:  Index [UQ__Objects__5B8F1484A615D428]    Script Date: 3/18/2019 8:23:40 PM ******/
ALTER TABLE [dbo].[Objects] ADD UNIQUE NONCLUSTERED 
(
	[ObjectName] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, SORT_IN_TEMPDB = OFF, IGNORE_DUP_KEY = OFF, ONLINE = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
GO
SET ANSI_PADDING ON
GO
/****** Object:  Index [UQ__Roles__8A2B6160D9E3438C]    Script Date: 3/18/2019 8:23:40 PM ******/
ALTER TABLE [dbo].[Roles] ADD UNIQUE NONCLUSTERED 
(
	[RoleName] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, SORT_IN_TEMPDB = OFF, IGNORE_DUP_KEY = OFF, ONLINE = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
GO
ALTER TABLE [dbo].[ActionSetDetails]  WITH CHECK ADD FOREIGN KEY([ActionID])
REFERENCES [dbo].[Actions] ([ActionID])
GO
ALTER TABLE [dbo].[ActionSetDetails]  WITH CHECK ADD FOREIGN KEY([ActionSetID])
REFERENCES [dbo].[ActionSets] ([ActionSetID])
GO
ALTER TABLE [dbo].[ConflictedActions]  WITH CHECK ADD FOREIGN KEY([ActionID1])
REFERENCES [dbo].[Actions] ([ActionID])
GO
ALTER TABLE [dbo].[ConflictedActions]  WITH CHECK ADD FOREIGN KEY([ACtionID2])
REFERENCES [dbo].[Actions] ([ActionID])
GO
ALTER TABLE [dbo].[ObjectContainerDetails]  WITH CHECK ADD FOREIGN KEY([ContainerID])
REFERENCES [dbo].[ObjectContainers] ([ContainerID])
GO
ALTER TABLE [dbo].[ObjectContainerDetails]  WITH CHECK ADD FOREIGN KEY([ObjectID])
REFERENCES [dbo].[Objects] ([ObjectID])
GO
ALTER TABLE [dbo].[Permissions]  WITH CHECK ADD FOREIGN KEY([ActionID])
REFERENCES [dbo].[Actions] ([ActionID])
GO
ALTER TABLE [dbo].[Permissions]  WITH CHECK ADD FOREIGN KEY([ObjectID])
REFERENCES [dbo].[Objects] ([ObjectID])
GO
USE [master]
GO
ALTER DATABASE [RBAC2] SET  READ_WRITE 
GO
