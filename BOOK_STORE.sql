USE [master]
GO
/****** Object:  Database [BOOK_STORE]    Script Date: 12/12/2022 7:07:46 PM ******/
CREATE DATABASE [BOOK_STORE]
 CONTAINMENT = NONE
 ON  PRIMARY 
( NAME = N'BOOK_STORE', FILENAME = N'C:\Program Files\Microsoft SQL Server\MSSQL15.MSSQLEXPRESS2019\MSSQL\DATA\BOOK_STORE.mdf' , SIZE = 8192KB , MAXSIZE = UNLIMITED, FILEGROWTH = 65536KB )
 LOG ON 
( NAME = N'BOOK_STORE_log', FILENAME = N'C:\Program Files\Microsoft SQL Server\MSSQL15.MSSQLEXPRESS2019\MSSQL\DATA\BOOK_STORE_log.ldf' , SIZE = 8192KB , MAXSIZE = 2048GB , FILEGROWTH = 65536KB )
 WITH CATALOG_COLLATION = DATABASE_DEFAULT
GO
ALTER DATABASE [BOOK_STORE] SET COMPATIBILITY_LEVEL = 150
GO
IF (1 = FULLTEXTSERVICEPROPERTY('IsFullTextInstalled'))
begin
EXEC [BOOK_STORE].[dbo].[sp_fulltext_database] @action = 'enable'
end
GO
ALTER DATABASE [BOOK_STORE] SET ANSI_NULL_DEFAULT OFF 
GO
ALTER DATABASE [BOOK_STORE] SET ANSI_NULLS OFF 
GO
ALTER DATABASE [BOOK_STORE] SET ANSI_PADDING OFF 
GO
ALTER DATABASE [BOOK_STORE] SET ANSI_WARNINGS OFF 
GO
ALTER DATABASE [BOOK_STORE] SET ARITHABORT OFF 
GO
ALTER DATABASE [BOOK_STORE] SET AUTO_CLOSE OFF 
GO
ALTER DATABASE [BOOK_STORE] SET AUTO_SHRINK OFF 
GO
ALTER DATABASE [BOOK_STORE] SET AUTO_UPDATE_STATISTICS ON 
GO
ALTER DATABASE [BOOK_STORE] SET CURSOR_CLOSE_ON_COMMIT OFF 
GO
ALTER DATABASE [BOOK_STORE] SET CURSOR_DEFAULT  GLOBAL 
GO
ALTER DATABASE [BOOK_STORE] SET CONCAT_NULL_YIELDS_NULL OFF 
GO
ALTER DATABASE [BOOK_STORE] SET NUMERIC_ROUNDABORT OFF 
GO
ALTER DATABASE [BOOK_STORE] SET QUOTED_IDENTIFIER OFF 
GO
ALTER DATABASE [BOOK_STORE] SET RECURSIVE_TRIGGERS OFF 
GO
ALTER DATABASE [BOOK_STORE] SET  DISABLE_BROKER 
GO
ALTER DATABASE [BOOK_STORE] SET AUTO_UPDATE_STATISTICS_ASYNC OFF 
GO
ALTER DATABASE [BOOK_STORE] SET DATE_CORRELATION_OPTIMIZATION OFF 
GO
ALTER DATABASE [BOOK_STORE] SET TRUSTWORTHY OFF 
GO
ALTER DATABASE [BOOK_STORE] SET ALLOW_SNAPSHOT_ISOLATION OFF 
GO
ALTER DATABASE [BOOK_STORE] SET PARAMETERIZATION SIMPLE 
GO
ALTER DATABASE [BOOK_STORE] SET READ_COMMITTED_SNAPSHOT OFF 
GO
ALTER DATABASE [BOOK_STORE] SET HONOR_BROKER_PRIORITY OFF 
GO
ALTER DATABASE [BOOK_STORE] SET RECOVERY SIMPLE 
GO
ALTER DATABASE [BOOK_STORE] SET  MULTI_USER 
GO
ALTER DATABASE [BOOK_STORE] SET PAGE_VERIFY CHECKSUM  
GO
ALTER DATABASE [BOOK_STORE] SET DB_CHAINING OFF 
GO
ALTER DATABASE [BOOK_STORE] SET FILESTREAM( NON_TRANSACTED_ACCESS = OFF ) 
GO
ALTER DATABASE [BOOK_STORE] SET TARGET_RECOVERY_TIME = 60 SECONDS 
GO
ALTER DATABASE [BOOK_STORE] SET DELAYED_DURABILITY = DISABLED 
GO
ALTER DATABASE [BOOK_STORE] SET ACCELERATED_DATABASE_RECOVERY = OFF  
GO
ALTER DATABASE [BOOK_STORE] SET QUERY_STORE = OFF
GO
USE [BOOK_STORE]
GO
/****** Object:  Table [dbo].[OrderDetails]    Script Date: 12/12/2022 7:07:46 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[OrderDetails](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[sku] [varchar](10) NOT NULL,
	[order_id] [int] NOT NULL,
	[price] [float] NOT NULL,
	[quantity] [int] NOT NULL,
	[total] [float] NOT NULL,
 CONSTRAINT [PK_OrderDetails] PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[Orders]    Script Date: 12/12/2022 7:07:46 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Orders](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[dateBuy] [datetime] NOT NULL,
	[total] [float] NULL,
	[name] [nvarchar](32) NULL,
	[phone] [varchar](20) NULL,
	[address] [nvarchar](400) NULL,
	[username] [varchar](20) NULL,
 CONSTRAINT [PK_Order] PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[Product]    Script Date: 12/12/2022 7:07:46 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Product](
	[sku] [varchar](10) NOT NULL,
	[name] [nvarchar](50) NOT NULL,
	[description] [nvarchar](250) NULL,
	[quantity] [int] NOT NULL,
	[price] [float] NOT NULL,
	[status] [bit] NOT NULL,
 CONSTRAINT [PK_Product] PRIMARY KEY CLUSTERED 
(
	[sku] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[Registration]    Script Date: 12/12/2022 7:07:46 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Registration](
	[username] [varchar](20) NOT NULL,
	[password] [varchar](64) NOT NULL,
	[lastname] [nvarchar](100) NOT NULL,
	[isAdmin] [bit] NOT NULL,
 CONSTRAINT [PK_Registration] PRIMARY KEY CLUSTERED 
(
	[username] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
SET IDENTITY_INSERT [dbo].[OrderDetails] ON 
GO
INSERT [dbo].[OrderDetails] ([id], [sku], [order_id], [price], [quantity], [total]) VALUES (150, N'BOOK04', 114, 7, 1, 7)
GO
INSERT [dbo].[OrderDetails] ([id], [sku], [order_id], [price], [quantity], [total]) VALUES (151, N'BOOK02', 114, 10, 1, 10)
GO
INSERT [dbo].[OrderDetails] ([id], [sku], [order_id], [price], [quantity], [total]) VALUES (152, N'BOOK01', 115, 12, 1, 12)
GO
SET IDENTITY_INSERT [dbo].[OrderDetails] OFF
GO
SET IDENTITY_INSERT [dbo].[Orders] ON 
GO
INSERT [dbo].[Orders] ([id], [dateBuy], [total], [name], [phone], [address], [username]) VALUES (114, CAST(N'2022-12-12T19:03:05.347' AS DateTime), 17, N'Duy Duc', N'0934968395', N'HCM.C, Vietnam', N'duyduc')
GO
INSERT [dbo].[Orders] ([id], [dateBuy], [total], [name], [phone], [address], [username]) VALUES (115, CAST(N'2022-12-12T19:04:09.177' AS DateTime), 12, N'Duy Duc', N'09349686986', N'HCM.C, Vietnam', N'duyduc')
GO
SET IDENTITY_INSERT [dbo].[Orders] OFF
GO
INSERT [dbo].[Product] ([sku], [name], [description], [quantity], [price], [status]) VALUES (N'BOOK01', N'Java Core', N'Intro to Java Core in the first step', 100, 12, 1)
GO
INSERT [dbo].[Product] ([sku], [name], [description], [quantity], [price], [status]) VALUES (N'BOOK02', N'Servlet', N'Get started with Servlet', 90, 10, 1)
GO
INSERT [dbo].[Product] ([sku], [name], [description], [quantity], [price], [status]) VALUES (N'BOOK03', N'JDBC', N'Interact with DB by JDBC', 35, 7, 1)
GO
INSERT [dbo].[Product] ([sku], [name], [description], [quantity], [price], [status]) VALUES (N'BOOK04', N'JSP', N'Intro to JSP', 81, 7, 1)
GO
INSERT [dbo].[Product] ([sku], [name], [description], [quantity], [price], [status]) VALUES (N'BOOK05', N'JavaBeans', N'Understanding about Beans in Java', 89, 10, 1)
GO
INSERT [dbo].[Product] ([sku], [name], [description], [quantity], [price], [status]) VALUES (N'BOOK06', N'JSTL', N'JSTL in action', 94, 10, 1)
GO
INSERT [dbo].[Product] ([sku], [name], [description], [quantity], [price], [status]) VALUES (N'BOOK07', N'MVC2', N'MVC2 and related core concepts', 90, 12, 1)
GO
INSERT [dbo].[Product] ([sku], [name], [description], [quantity], [price], [status]) VALUES (N'BOOK08', N'Tomcat', N'Make relationship with CATALINA', 10, 11, 1)
GO
INSERT [dbo].[Product] ([sku], [name], [description], [quantity], [price], [status]) VALUES (N'BOOK09', N'Hibernate', N'JPA', 2, 13, 0)
GO
INSERT [dbo].[Product] ([sku], [name], [description], [quantity], [price], [status]) VALUES (N'BOOK10', N'Struts', N'Struts Framework', 5, 45, 1)
GO
INSERT [dbo].[Product] ([sku], [name], [description], [quantity], [price], [status]) VALUES (N'BOOK11', N'Spring', N'Spring Boot', 0, 55, 1)
GO
INSERT [dbo].[Registration] ([username], [password], [lastname], [isAdmin]) VALUES (N'administrator', N'15e2b0d3c33891ebb0f1ef609ec419420c20e320ce94c65fbc8c3312448eb225', N'Administrator', 1)
GO
INSERT [dbo].[Registration] ([username], [password], [lastname], [isAdmin]) VALUES (N'duyduc', N'15e2b0d3c33891ebb0f1ef609ec419420c20e320ce94c65fbc8c3312448eb225', N'Duy Duc', 0)
GO
INSERT [dbo].[Registration] ([username], [password], [lastname], [isAdmin]) VALUES (N'gialinh', N'6e39f30254c8129390cd6963f649f076241bfdca15ce7ad2725b14029761b6d4', N'Gia Linh', 0)
GO
INSERT [dbo].[Registration] ([username], [password], [lastname], [isAdmin]) VALUES (N'haanhh', N'ef797c8118f02dfb649607dd5d3f8c7623048c9c063d532cc95c5ed7a898a64f', N'Ha Anh', 0)
GO
INSERT [dbo].[Registration] ([username], [password], [lastname], [isAdmin]) VALUES (N'hathanh', N'59629c7a393979e9d27ec842e3c8c086aab47f68215e2133d8f1feeefefb33f8', N'Ha Thanh', 0)
GO
INSERT [dbo].[Registration] ([username], [password], [lastname], [isAdmin]) VALUES (N'khanhkk', N'15e2b0d3c33891ebb0f1ef609ec419420c20e320ce94c65fbc8c3312448eb225', N'Khanh Tieu Nhi', 0)
GO
INSERT [dbo].[Registration] ([username], [password], [lastname], [isAdmin]) VALUES (N'khanhkt', N'15e2b0d3c33891ebb0f1ef609ec419420c20e320ce94c65fbc8c3312448eb225', N'Khanh Dai Ca', 1)
GO
INSERT [dbo].[Registration] ([username], [password], [lastname], [isAdmin]) VALUES (N'lananh', N'5994471abb01112afcc18159f6cc74b4f511b99806da59b3caf5a9c173cacfc5', N'Lan Anh Nguyen', 0)
GO
INSERT [dbo].[Registration] ([username], [password], [lastname], [isAdmin]) VALUES (N'laphuonganh', N'59ec397ab6927c0f1a313f3f8fb6bcaa39f2e13bce374e810a81f3ab43c58594', N'La Phuong Anh', 0)
GO
INSERT [dbo].[Registration] ([username], [password], [lastname], [isAdmin]) VALUES (N'linhnguyen', N'e8de8797f2f344a4f0ff4b3c461c77bd184ecb77afee7996faef44bb9a0af7e5', N'Linh Nguyen', 0)
GO
INSERT [dbo].[Registration] ([username], [password], [lastname], [isAdmin]) VALUES (N'maianh', N'8d75d90e1b1011063297c17473fbdea951920aaef3e56c6d8af082c5e3fa3303', N'Mia Nguyen', 0)
GO
INSERT [dbo].[Registration] ([username], [password], [lastname], [isAdmin]) VALUES (N'minhanh', N'0c60c856346fe343925290ec4698e72c4d435b167a5061611662c600b5becaab', N'Minh Anh', 0)
GO
INSERT [dbo].[Registration] ([username], [password], [lastname], [isAdmin]) VALUES (N'minhthy', N'b1b1f7bd8062b541787ece696b40246c4d89af68d484fe9dc7fda01247328b1b', N'Minh Thy', 0)
GO
INSERT [dbo].[Registration] ([username], [password], [lastname], [isAdmin]) VALUES (N'theanh', N'b1e03c11fec2db2dfd07ed85b05b1c034d0faddd2414b71afe70454208e9537a', N'The Anh', 0)
GO
INSERT [dbo].[Registration] ([username], [password], [lastname], [isAdmin]) VALUES (N'thienminh', N'26bcb10239a785c819d3f816b8117553d9549571d95623a19119d98f527a7107', N'Thien Minh', 0)
GO
INSERT [dbo].[Registration] ([username], [password], [lastname], [isAdmin]) VALUES (N'tueanh', N'f0b65f4640ad5bdb84dbd558c7b779ddaee9a2652c76256e74eb0aff4e856c8b', N'Tue Anh', 1)
GO
INSERT [dbo].[Registration] ([username], [password], [lastname], [isAdmin]) VALUES (N'tueminh', N'450b2e5f3611c0d1ac217a01743fb16f7b982b560b15760b05bcffe79b3da495', N'Tue Minh', 0)
GO
ALTER TABLE [dbo].[OrderDetails]  WITH CHECK ADD  CONSTRAINT [FK_OrderDetails_Order] FOREIGN KEY([order_id])
REFERENCES [dbo].[Orders] ([id])
ON DELETE CASCADE
GO
ALTER TABLE [dbo].[OrderDetails] CHECK CONSTRAINT [FK_OrderDetails_Order]
GO
ALTER TABLE [dbo].[OrderDetails]  WITH CHECK ADD  CONSTRAINT [FK_OrderDetails_Product] FOREIGN KEY([sku])
REFERENCES [dbo].[Product] ([sku])
GO
ALTER TABLE [dbo].[OrderDetails] CHECK CONSTRAINT [FK_OrderDetails_Product]
GO
ALTER TABLE [dbo].[Orders]  WITH CHECK ADD  CONSTRAINT [FK_Orders_Registration] FOREIGN KEY([username])
REFERENCES [dbo].[Registration] ([username])
GO
ALTER TABLE [dbo].[Orders] CHECK CONSTRAINT [FK_Orders_Registration]
GO
USE [master]
GO
ALTER DATABASE [BOOK_STORE] SET  READ_WRITE 
GO
